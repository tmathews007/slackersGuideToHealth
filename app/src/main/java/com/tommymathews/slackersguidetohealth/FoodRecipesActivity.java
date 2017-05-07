package com.tommymathews.slackersguidetohealth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Food;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

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
            if (food.getImagePath().length() > 0) {
                Uri uri = Uri.parse(food.getImagePath());
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    picImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                picImageView.setVisibility(View.GONE);
            }
            if (food.getRecommendation().length() == 0) {
                foodSuggestionTextView.setText("This food must be delicious because it's only "+
                        food.getCalorieLevel() +" calories!");
            } else {
                foodSuggestionTextView.setText(food.getRecommendation());
            }
            recipeIdeaTextView.setText(food.getName());
            ingredientsTextView.setText("Ingredients: "+food.getIngredients());
            processTextView.setText("Directions: "+food.getRecipe());
        }

    }

    @Override
    public void onBackPressed() {
        dialogPopup();
    }

    private void dialogPopup() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Did you make "+ recipeIdeaTextView.getText() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);
                        String username = sp.getString(DbSchema.EMAIL, null);
                        DependencyFactory.getUserService(getApplicationContext()).incrementFoodProgress(username);
                        finish();
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .show();
    }

}