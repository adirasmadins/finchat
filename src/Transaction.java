import java.io.*;
import java.util.*;

public class Transaction implements Serializable{

    protected static final long serialVersionUID = 1112122200L;
    private String item_name;
    private String description;
    private Double pricesold;
    private Date date;
    private String status;
    private Address address;
    private String street;
    private String city;
    private String province;
    private String postalcode;
    private String country;


    Transaction(String item_name, String description, Double pricesold, Date date, Address address, String status){
        this.item_name = item_name;
        this.description = description;
        this.pricesold = pricesold;
        this.date = date;
        this.status = status;
        this.address = address;
        this.street = this.address.getStreet();
        this.city = this.address.getCity();
        this.province = this.address.getProvince();
        this.postalcode = this.address.getPostalcode();
        this.country = this.address.getPostalcode();

    }


    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPricesold() {
        return pricesold;
    }

    public void setPricesold(Double pricesold) {
        this.pricesold = pricesold;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
