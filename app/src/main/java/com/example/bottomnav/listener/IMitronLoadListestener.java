package com.example.bottomnav.listener;

import com.example.bottomnav.FoodModel;

import java.util.List;

public interface IMitronLoadListestener {
    void onMitronLoadSuccess(List<FoodModel> foodModelList);

    void onMitronLoadFailed(String message);
}
