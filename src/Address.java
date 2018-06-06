import java.io.*;

public class Address implements Serializable{

    protected static final long serialVersionUID = 1112122200L;
    private String street;
    private String city;
    private String province;
    private String country;
    private String postalcode;

    Address(String street, String city, String province, String country, String postalcode){

        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalcode = postalcode;

    }

    public void setStreet(String street){

        this.street = street;
    }

    public String getStreet(){

        return street;
    }


    public void setCity(String city){

        this.city = city;
    }

    public String getCity(){

        return city;
    }

    public void setProvince(String province){

        this.province = province;
    }

    public String getProvince(){

        return province;
    }

    public void setCountry(String country){

        this.country = country;
    }

    public String getCountry(){

        return country;
    }

    public void setPostalcode(String postalcode){

        this.postalcode = postalcode;
    }

    public String getPostalcode(){

        return postalcode;
    }


}
