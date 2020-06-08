package com.mj.pacholiaonlinebusinessapp.model;

public class Cart {
    String pid,productname,productdesc,productprice,productquantity,date,time,perpricequantity,category;
    int productquantityamount;

    public Cart(String pid, String productname, String productdesc, String productprice, String productquantity, String date, String time, String perpricequantity, String category, int productquantityamount) {
        this.pid = pid;
        this.productname = productname;
        this.productdesc = productdesc;
        this.productprice = productprice;
        this.productquantity = productquantity;
        this.date = date;
        this.time = time;
        this.perpricequantity = perpricequantity;
        this.category = category;
        this.productquantityamount = productquantityamount;
    }

    public int getProductquantityamount() {
        return productquantityamount;
    }

    public void setProductquantityamount(int productquantityamount) {
        this.productquantityamount = productquantityamount;
    }

    public Cart()
    {

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductdesc() {
        return productdesc;
    }

    public void setProductdesc(String productdesc) {
        this.productdesc = productdesc;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(String productquantity) {
        this.productquantity = productquantity;
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

    public String getPerpricequantity() {
        return perpricequantity;
    }

    public void setPerpricequantity(String perpricequantity) {
        this.perpricequantity = perpricequantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
