
import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class JsonParser {
    //Saves new link with changed page number
    private String nextPage;
    private Pagination pageInfo;

    //due to requirements program has to print to stdout amount of HTTP-requests and amount of extracted products
    private int amountOfRequests;
    private int amountOfProducts;

    public Pagination getPagination(WebLinkCreator wlc) throws IOException {
        pageInfo = new Pagination();
        nextPage = wlc.getLink();

        URL firstPage = new URL(nextPage);
        //Adding user-agent to not getting blocked
        HttpURLConnection httpConn = (HttpURLConnection) firstPage.openConnection();
        httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 " +
                                    "Firefox/56.0");

        amountOfRequests = 0;
        JSONObject obj = new JSONObject();
        JSONObject gPI = new JSONObject();
        //First http-request to get Pagination-info
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(firstPage.openStream()))) {
            String jsonFromReader = readAll(reader);
            amountOfRequests++;

            obj = new JSONObject(jsonFromReader);
            gPI = obj.getJSONObject("pagination");
            pageInfo.setLast((int) gPI.get("last"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // first cycle, that runs over all pages
        JSONArray toSave = new JSONArray();

        JSONObject result = new JSONObject();
        //result  have to save the products from json
        FileWriter aboutYouJson = new FileWriter("AboutYou.json");


        for(int i = 0; i < pageInfo.getLast(); i++){
            nextPage = wlc.getLink();

            firstPage = new URL(nextPage);
            //Adding user-agent to not getting blocked
            httpConn = (HttpURLConnection) firstPage.openConnection();
            httpConn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:56.0) Gecko/20100101 " +
                    "Firefox/56.0");



            try(BufferedReader reader = new BufferedReader(new InputStreamReader(firstPage.openStream()))){
                String jsonFromReader = readAll(reader);
                amountOfRequests++;
                obj = new JSONObject(jsonFromReader);
                gPI = obj.getJSONObject("pagination");

                pageInfo.setTotal((int) gPI.get("total"));
                pageInfo.setLast((int) gPI.get("last"));
                pageInfo.setPerPage((int) gPI.get("perPage"));
                pageInfo.setPage((int) gPI.get("page"));
                pageInfo.setNext((int) gPI.get("next"));
                pageInfo.setCurrent((int) gPI.get("current"));
                System.out.println(pageInfo.toString());

                for(int j = 0; j < pageInfo.getCurrent(); j++){
                    JSONObject productObjects = new JSONObject();
                    JSONObject attributes  = new JSONObject();
                    attributes = obj.getJSONArray("entities").getJSONObject(j).getJSONObject("attributes");
                    int howManyColors = 0;
                        try{
                             howManyColors = attributes.getJSONObject("colorDetail").getJSONArray("values").length();
                        }
                        catch (Exception e){
                            System.out.println(e.getMessage());
                        }


                        if(howManyColors != 0){
                            String[] colorVariants = new String[howManyColors];
                            for(int c = 0; c < howManyColors; c++){
                                colorVariants[c] = (String) attributes.getJSONObject("colorDetail").getJSONArray("values")
                                        .getJSONObject(c).get("value");

                            }

                            productObjects.put("colors", colorVariants);
                        }


                    int min = (int) obj.getJSONArray("entities").getJSONObject(j).getJSONObject("priceRange")
                            .getJSONObject("min").get("withTax");
                    int max = (int) obj.getJSONArray("entities").getJSONObject(j).getJSONObject("priceRange")
                            .getJSONObject("max").get("withTax");
                    int[] priceRange = {min, max};
                    int id = (int) obj.getJSONArray("entities").getJSONObject(j).get("id");

                    productObjects.put("name", attributes.getJSONObject("name").getJSONObject("values").get("label"));
                    productObjects.put("brand", attributes.getJSONObject("brand").getJSONObject("values").get("label"));

                    productObjects.put("priceRange", priceRange);
                    productObjects.put("productID", id);

                    toSave.put(productObjects);
                }
                result.put("products", toSave);
                aboutYouJson.write(result.toString());
                wlc.nextPage(pageInfo.getNext());




            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    //    Method converts json from url to a string
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
