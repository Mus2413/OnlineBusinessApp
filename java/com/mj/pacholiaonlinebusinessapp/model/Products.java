package com.mj.pacholiaonlinebusinessapp.model;

public class Products {
    private String pid, date, time, description, pname, price, imageurl, category,perpricequantity;

    public Products() {
    }

    public Products(String pid, String date, String time, String description, String pname, String price, String imageurl, String category, String perpricequantity) {
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.description = description;
        this.pname = pname;
        this.price = price;
        this.imageurl = imageurl;
        this.category = category;
        this.perpricequantity = perpricequantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPerpricequantity() {
        return perpricequantity;
    }

    public void setPerpricequantity(String perpricequantity) {
        this.perpricequantity = perpricequantity;
    }
}