package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Food;

import java.io.IOException;

import static com.tommymathews.slackersguidetohealth.FoodFragment.FOOD;
import static com.tommymathews.slackersguidetohealth.R.id.foodPic;

/**
 * Created by Ashwin on 4/4/2017.
 */

public class FoodRecipesActivity extends ActivityWithMenu {
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
        if (intent.getSerializableExtra(FOOD) != null) {
            Food food = (Food) intent.getSerializableExtra(FOOD);
            Uri uri = Uri.parse(food.getImagePath());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                picImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //TODO ADD FOOD SUGGEST
            //  foodSuggestionTextView.setText();
            if (food.getRecommendation().length() == 0) {
                //TODO ADD FOOD SUGGEST
            } else {
                foodSuggestionTextView.setText(food.getRecommendation());
            }
            recipeIdeaTextView.setText(food.getName());
            ingredientsTextView.setText("Ingredients: "+food.getIngredients());
            processTextView.setText("Directions: "+food.getRecipe());
        }

    }

}