package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tommymathews.slackersguidetohealth.model.Food;
import com.tommymathews.slackersguidetohealth.service.FoodService;

import java.util.ArrayList;
import java.util.List;

import static com.tommymathews.slackersguidetohealth.FoodFragment.CALORIES;
import static com.tommymathews.slackersguidetohealth.R.id.foodPic;

/**
 * Created by Ashwin on 4/4/2017.
 */

public class FoodActivity extends ActivityWithMenu {
    private ImageView picImageView;
    private TextView foodSuggestionTextView;
    private TextView recipeIdeaTextView;
    private TextView ingredientsTextView;
    private TextView processTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_suggestions);

        picImageView = (ImageView) findViewById(foodPic);
        foodSuggestionTextView = (TextView) findViewById(R.id.foodSuggestion);
        recipeIdeaTextView = (TextView) findViewById(R.id.recipeIdea);
        ingredientsTextView = (TextView) findViewById(R.id.ingredients);
        processTextView = (TextView) findViewById(R.id.process);

        Intent intent = getIntent();
        int calories = intent.getIntExtra(CALORIES, 0);
        FoodService foodService = DependencyFactory.getFoodService(this);
        int low = 0, high = 0;
        List<Food> foods = new ArrayList<Food>();

        if(calories < 500){
            high = 500;
        } else if(calories>=500 && calories<750) {
            low = 500;
            high = 750;
        } else if(calories>=750 && calories<1000){
            low = 750;
            high = 1000;
        } else if(calories>=1000 && calories<=1500){
            low = 1000;
            high = 1500;
        } else{
            Toast.makeText(this, "Talk to a professional" +
                    " to develop a diet plan. Your calorie intake is too high.", Toast.LENGTH_SHORT).show();
        }

        if (calories <= 1500) {
            foods = foodService.getFoodByCalorieRange(low, high);
        }

        if (!foods.isEmpty()) {
            Food food = foods.get((int)(Math. random() * foods.size()));
            picImageView.setImageBitmap(food.getImage());
            //TODO ADD FOOD SUGGEST
            //  foodSuggestionTextView.setText();
            recipeIdeaTextView.setText(food.getName());
            ingredientsTextView.setText("Ingredients: "+food.getIngredients());
            processTextView.setText("Process: "+food.getRecipe());
        }

    }
}