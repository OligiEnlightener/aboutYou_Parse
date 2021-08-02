public class WebLinkCreator {
    private String link;
    private String pageNumber;

    public WebLinkCreator(String link){
        this.link = link;
        pageNumber = "page=1";
    }

    public void nextPage(int next){
        String tempLink = getLink();
        String nextPage = "page=" + next;
        tempLink = tempLink.replace(pageNumber, nextPage);
        pageNumber = nextPage;
        this.link = tempLink;
    }
    public String getLink() {
        return link;
    }

    public String getPageNumber() {
        return pageNumber;
    }
}
