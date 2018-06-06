import java.io.*;

public class Communication implements Serializable {

    protected static final long serialVersionUID = 1112122200L;
    private int task;
    private String fields;
    private Address address;
    private Item item;
    private User user;
    static final int LOGIN = 0, RETURNITEMS = 1, ADDTRANSACTION = 2, ADDITEM = 3, ADDCREDIT = 4, EDITFN = 5, EDITLN = 6, EDITPW = 7
            ,EDITADDRESS = 8, EDITSELLITEMS = 9, DELETESELLITEM = 10,
            VIEWBOUGHT = 11, VIEWSOLD = 12, LOGOUT = 13, RETURNSELLINGITEMS = 14,
            RETURNUSERINFO = 15, RETURNCREDIT = 16, CHECKSELLERONLINE=17, GETNEWSESSION = 18;

    Communication(int task, String fields) {
        this.task = task;
        this.fields = fields;
    }

    Communication(int task, Address address){
        this.task = task;
        this.address = address;
    }

    Communication(int task, Item item){
        this.task = task;
        this.item = item;
    }

    Communication(int task, User user){
        this.task = task;
        this.fields = fields;
        this.user = user;
    }

    Communication(int task, Item item, User user){
        this.task = task;
        this.item = item;
        this.user = user;
    }

    Communication(int task){
        this.task = task;
    }

    public void setTask(int task) {
        this.task = task;
    }

    int getTask() {
        return task;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    String getFields() {
        return fields;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
