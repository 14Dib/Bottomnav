package com.example.bottomnav.listener;

import com.example.bottomnav.CartModel;
import com.example.bottomnav.FoodModel;

import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<CartModel> cartModelList);

    void onCartLoadFailed(String message);
}
