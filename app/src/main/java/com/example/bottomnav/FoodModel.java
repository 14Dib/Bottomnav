package com.example.bottomnav;

public class FoodModel {
    private String key, name, price, image, description;

    public FoodModel(){

    }

    public FoodModel(String key, String name, String description, String price, String image) {
        this.key = key;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
