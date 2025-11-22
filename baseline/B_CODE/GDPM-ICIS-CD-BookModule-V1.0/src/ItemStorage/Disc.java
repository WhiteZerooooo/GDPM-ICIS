package ItemStorage;

// 光盘物品类
public class Disc extends Item {
    private String ProducerName; // 出品者名字
    private String ProducionYear; // 出品年份
    private String VedioLength; // 视频时长(s)

    public Disc() {

    }

    public Disc(String SerialNumber, String Title, String Author, String Rating, String ProducerName,
            String ProducionYear, String VedioLength) {
        super(SerialNumber, Title, Author, Rating);
        this.ProducerName = ProducerName;
        this.ProducionYear = ProducionYear;
        this.VedioLength = VedioLength;
    }

    public void setProducerName(String ProducerName) {
        this.ProducerName = ProducerName;
    }

    public void setProducionYear(String ProducionYear) {
        this.ProducionYear = ProducionYear;
    }

    public void setVedioLength(String VedioLength) {
        this.VedioLength = VedioLength;
    }

    public String getProducerName() {
        return ProducerName;
    }

    public String getProducionYear() {
        return ProducionYear;
    }

    public String getVedioLength() {
        return VedioLength;
    }
}
