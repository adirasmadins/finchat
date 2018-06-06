import java.io.*;

public class Item implements Serializable{

    protected static final long serialVersionUID = 1112122200L;
    private String name;
    private String description;
    private Double price_posted;
    private int item_code;

    public Item(String name, String description, Double price_posted, int item_code) {
        this.name = name;
        this.description = description;
        this.price_posted = price_posted;
        this.item_code = item_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice_posted() {
        return price_posted;
    }

    public void setPrice_posted(Double price_posted) {
        this.price_posted = price_posted;
    }

    public int getItem_code() {
        return item_code;
    }

    public void setItem_code(int item_code) {
        this.item_code = item_code;
    }
}
