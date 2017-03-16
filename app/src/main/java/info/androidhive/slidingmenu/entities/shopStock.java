package info.androidhive.slidingmenu.entities;

/**
 * Created by Juan on 14/03/2017.
 */

public class ShopStock {

    private String street;
    private int stock;
    private String city;
    private String name;

    public ShopStock(){

    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
