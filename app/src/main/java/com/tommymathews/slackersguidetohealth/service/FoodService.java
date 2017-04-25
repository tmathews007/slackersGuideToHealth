package com.tommymathews.slackersguidetohealth.service;


import com.tommymathews.slackersguidetohealth.model.Food;

import java.util.List;

/**
 * Created by cj on 4/25/17.
 */

public interface FoodService {
    public void addFood(Food food);
    public Food getFoodByName(String food);
    public List<Food> getAllFoods();
}
