package com.example.bottomnav;

import java.util.List;

import javax.annotation.Nullable;

public class CheckoutModel {
    private String name, date, time, user_Name, user_Phone, user_Address;
    private int totalquantity;
    private float totalPrice;
    private List<CartModel> cart_list;

    public CheckoutModel(String name, String date, String time, int totalquantity, float totalPrice,String user_Name, String user_Phone,String user_Address,@Nullable List<CartModel> cart_list) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.totalquantity = totalquantity;
        this.totalPrice = totalPrice;
        this.user_Name = user_Name;
        this.user_Phone = user_Phone;
        this.user_Address = user_Address;
        this.cart_list = cart_list;
    }

    public CheckoutModel() {

    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Phone() {
        return user_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        this.user_Phone = user_Phone;
    }

    public String getUser_Address() {
        return user_Address;
    }

    public void setUser_Address(String user_Address) {
        this.user_Address = user_Address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(int totalquantity) {
        this.totalquantity = totalquantity;
    }

    public List<CartModel> getCart_list() {
        return cart_list;
    }

    public void setCart_list(List<CartModel> cart_list) {
        this.cart_list = cart_list;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
