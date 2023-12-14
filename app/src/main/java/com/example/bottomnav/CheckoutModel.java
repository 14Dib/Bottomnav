package com.example.bottomnav;

import java.util.List;

import javax.annotation.Nullable;

public class CheckoutModel {
    private String name, date, time;
    private int totalquantity;
    private float totalPrice;
    private List<CartModel> cart_list;

    public CheckoutModel(String name, String date, String time, int totalquantity, float totalPrice,@Nullable List<CartModel> cart_list) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.totalquantity = totalquantity;
        this.totalPrice = totalPrice;
        this.cart_list = cart_list;
    }

    public CheckoutModel() {

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
