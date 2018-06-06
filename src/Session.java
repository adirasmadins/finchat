import java.io.*;

public class Session implements Serializable {

    protected static final long serialVersionUID = 1112122200L;

    private int sessionid;
    private User buyer;
    private User seller;
    private Double price;
    private Item item;

    Session(int sessionid, User buyer, User seller, double price, Item item) {
        this.sessionid = sessionid;
        this.buyer = buyer;
        this.seller = seller;
        this.price = price;
        this.item = item;
    }

    public int getSessionid() {
        return sessionid;
    }

    public void setSessionid(int sessionid) {
        this.sessionid = sessionid;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}

