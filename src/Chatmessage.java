import java.io.*;

public class Chatmessage implements Serializable {

    protected static final long serialVersionUID = 1112122200L;


    private int type;
    private String msg;
    private User buyer;
    private Item item;
    static final int MESSAGE = 1, NEWSESSION = 2, CHATEXIT = 3;
    private Session session;

    Chatmessage(Session session, int type, String msg) {
        this.session = session;
        this.type = type;
        this.msg = msg;
    }


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

