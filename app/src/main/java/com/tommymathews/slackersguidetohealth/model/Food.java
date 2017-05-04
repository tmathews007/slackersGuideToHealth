package com.tommymathews.slackersguidetohealth.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Ashwin on 4/25/2017.
 */

public class Food implements Serializable {
    private String id = UUID.randomUUID().toString();
    private int calorieLevel;
    private String recommendation;
    private String recipe;
    private String name;
    private String ingredients;
    private String imagePath;

    public Food(int calLevel, String rec, String name, String ingred, String image){
        this.calorieLevel = calLevel;
        this.recipe = rec;
        this.name = name;
        this.ingredients = ingred;
        this.imagePath = image;
    }

    public Food(int calLevel, String recommendation, String rec, String name, String ingred, String image){
        this.calorieLevel = calLevel;
        this.recommendation = recommendation;
        this.recipe = rec;
        this.name = name;
        this.ingredients = ingred;
        this.imagePath = image;
    }

    public Food(String id, int calLevel, String recommendation, String rec, String name, String ingred, String image){
        this.id = id;
        this.calorieLevel = calLevel;
        this.recommendation = recommendation;
        this.recipe = rec;
        this.name = name;
        this.ingredients = ingred;
        this.imagePath = image;
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

    public String getImagePath() {
        return imagePath;
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

    public void setImagePath(String image) {
        this.imagePath = image;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
