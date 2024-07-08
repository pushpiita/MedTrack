package com.example.myapplication;

public class Product {
    private int id;
    private String productName;
    private double buyingPrice;
    private double sellingPrice;
    private int quantity;
    private String companyName;
    private String expiredDate;
    private String buyingDate;

    public Product(int id, String productName, double buyingPrice, double sellingPrice, int quantity, String companyName, String expiredDate, String buyingDate) {
        this.id = id;
        this.productName = productName;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.companyName = companyName;
        this.expiredDate = expiredDate;
        this.buyingDate = buyingDate;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getBuyingDate() {
        return buyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        this.buyingDate = buyingDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", buyingPrice=" + buyingPrice +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", companyName='" + companyName + '\'' +
                ", expiredDate='" + expiredDate + '\'' +
                ", buyingDate='" + buyingDate + '\'' +
                '}';
    }
}
