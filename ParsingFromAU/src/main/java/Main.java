


import model.Pagination;

import java.io.IOException;




public class Main {

    public static void main(String[] args) throws IOException {
        WebLinkCreator jsonAboutYou = new WebLinkCreator("https://api-cloud.aboutyou.de/v1/products?with=" +
                "attributes%3Akey(brand|brandLogo|brandAlignment|name|quantityPerPack|plusSize|colorDetail" +
                "|sponsorBadge|sponsoredType|maternityNursing|exclusive|genderage|specialSizesProduct" +
                "|materialStyle|" + "sustainabilityIcons|assortmentType" +
                "|dROPS)%2CadvancedAttributes%3Akey(materialCompositionTextile|siblings)%2Cvariants%2Cvariants" +
                ".attributes%3Akey(shopSize|categoryShopFilterSizes|cup|cupsize|vendorSize|length" +
                "|dimension3|sizeType|sort)%2Cimages.attributes%3Alegacy(false)%3Akey(imageNextDetailLevel" +
                "|imageBackground|imageFocus|imageGender|imageType|imageView)%2CpriceRange&filters[category]" +
                "=20290&sortDir=desc&sortScore=category_scores&sortChannel=sponsored_web_default" +
                "&page=1&perPage=465&campaignKey=px&shopId=605");

        JsonParser parser = new JsonParser();
        Pagination getPageInfo = parser.getPagination(jsonAboutYou);
        System.out.println("Amount of requests: " + parser.getAmountOfRequests());
        System.out.println("Amount of extracted products" + parser.getAmountOfProducts());

    }
}
