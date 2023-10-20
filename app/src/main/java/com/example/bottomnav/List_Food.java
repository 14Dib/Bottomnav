package com.example.bottomnav;

public class List_Food {
    private String name;
    private String des;
    private double money;
    private int img;

    public List_Food(String name, String des, double money, int img) {
        this.name = name;
        this.des = des;
        this.money = money;
        this.img = img;
    }

    public static void remove(int position) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
