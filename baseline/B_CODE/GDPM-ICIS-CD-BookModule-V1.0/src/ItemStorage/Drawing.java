package ItemStorage;

// 图画物品类
public class Drawing extends Item {
    private String Nationality; // 出品国籍
    private String WorkLength; // 作品长度
    private String WorkWidth; // 作品宽度

    public Drawing() {

    }
    public Drawing(String SerialNumber, String Title, String Author, String Rating, String Nationality,
            String WorkLength, String WorkWidth) {
        super(SerialNumber, Title, Author, Rating);
        this.Nationality = Nationality;
        this.WorkLength = WorkLength;
        this.WorkWidth = WorkWidth;
    }

    public String getNationality() {
        return this.Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public String getWorkLength() {
        return this.WorkLength;
    }

    public void setWorkLength(String WorkLength) {
        this.WorkLength = WorkLength;
    }

    public String getWorkWidth() {
        return this.WorkWidth;
    }

    public void setWorkWidth(String WorkWidth) {
        this.WorkWidth = WorkWidth;
    }

}
