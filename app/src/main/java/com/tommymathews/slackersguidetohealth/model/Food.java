package com.tommymathews.slackersguidetohealth.model;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ashwin on 4/25/2017.
 */

public class Food extends AppCompatActivity{
    private int calorieLevel;
    private String recipe;
    private String name;
    private String ingredients;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Food(int calLevel, String rec, String name, String ingred){
        this.calorieLevel = calLevel;
        this.recipe = rec;
        this.name = name;
        this.ingredients = ingred;
    }

    public int getCalorieLevel() {
        return calorieLevel;
    }

    public String getRecipe() {
        return recipe;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setCalorieLevel(int cal) {
        calorieLevel = cal;
    }

    public void setRecipe(String descript) {
        recipe = descript;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(String ingred) {
        ingredients = ingred;
    }
}
