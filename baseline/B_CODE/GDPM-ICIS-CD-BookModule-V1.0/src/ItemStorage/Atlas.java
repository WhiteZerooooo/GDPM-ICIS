package ItemStorage;

// 图书物品类
public class Atlas extends Item {
    private String Publisher; // 出版社
    private String ISBN; // ISBN号
    private String Pages; // 页码数

    public Atlas() {

    }

    public Atlas(String SerialNumber, String Title, String Author, String Rating, String Publisher, String ISBN,
            String Pages) {
        super(SerialNumber, Title, Author, Rating);
        this.Publisher = Publisher;
        this.ISBN = ISBN;
        this.Pages = Pages;
    }

    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setPages(String Pages) {
        this.Pages = Pages;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getPages() {
        return Pages;
    }

}