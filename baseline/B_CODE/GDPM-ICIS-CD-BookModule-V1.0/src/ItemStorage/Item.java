package ItemStorage;

// 物品类
public class Item {
    private String SerialNumber; // 编号
    private String Title; // 标题
    private String Author; // 作者
    private String Rating; // 评级

    public Item() {

    }

    public Item(String SerialNumber, String Title, String Author, String Rating) {
        this.SerialNumber = SerialNumber;
        this.Title = Title;
        this.Author = Author;
        this.Rating = Rating;
    }

    public void setSerialNumber(String SerialNumber) {
        this.SerialNumber = SerialNumber;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAuthor(String Author) {
        this.Author = Author;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public String getRating() {
        return Rating;
    }
}
