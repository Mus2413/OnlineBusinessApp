package com.mj.pacholiaonlinebusinessapp.model;

public class AdminOrders {
    private String address,city,date,time,name,phonenumber,state,totalamount;

    public AdminOrders(String address, String city, String date, String time, String name, String phonenumber, String state, String totalamount) {
        this.address = address;
        this.city = city;
        this.date = date;
        this.time = time;
        this.name = name;
        this.phonenumber = phonenumber;
        this.state = state;
        this.totalamount = totalamount;
    }
    public AdminOrders(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }
}
