/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author fercho
 */
public class People {
    
    private int id;
    private String name;
    private String postalZip;
    private String phone;
    private String address;
    private String country;
    private String currency;
    private String alphanumeric;
    private int numberrange;
    private String region;
    private String text;
    private String email;
    private String list;

    public People(int id, String name, String postalZip, String phone, String address, String country, String currency, String alphanumeric, int numberrange, String region, String text, String email, String list) {
        this.id = id;
        this.name = name;
        this.postalZip = postalZip;
        this.phone = phone;
        this.address = address;
        this.country = country;
        this.currency = currency;
        this.alphanumeric = alphanumeric;
        this.numberrange = numberrange;
        this.region = region;
        this.text = text;
        this.email = email;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPostalZip() {
        return postalZip;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAlphanumeric() {
        return alphanumeric;
    }

    public int getNumberrange() {
        return numberrange;
    }

    public String getRegion() {
        return region;
    }

    public String getText() {
        return text;
    }

    public String getEmail() {
        return email;
    }

    public String getList() {
        return list;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPostalZip(String postalZip) {
        this.postalZip = postalZip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAlphanumeric(String alphanumeric) {
        this.alphanumeric = alphanumeric;
    }

    public void setNumberrange(int numberrange) {
        this.numberrange = numberrange;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setList(String list) {
        this.list = list;
    }
    
    
}
