package husseinm19.github.com.coffee_bike.logic;

public class Order {

    private String title;
    private int quan;
    private int price;
    private String date;
    private String id;

    public Order(String title, int quan, int price, String date, String id) {
        this.title = title;
        this.quan = quan;
        this.price = price;
        this.date = date;
        this.id = id;
    }

    public Order(){

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan(int quan) {
        this.quan = quan;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


