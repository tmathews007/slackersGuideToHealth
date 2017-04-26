package com.tommymathews.slackersguidetohealth.model;

import android.graphics.Bitmap;
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
    private Bitmap image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Food(int calLevel, String rec, String name, String ingred, Bitmap image){
        this.calorieLevel = calLevel;
        this.recipe = rec;
        this.name = name;
        this.ingredients = ingred;
        this.image = image;
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

    public Bitmap getImage() {
        return image;
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

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
