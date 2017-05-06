package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by cj on 5/6/17.
 */

public class FoodMain extends ActivityWithMenu {
    private Button getRecipe;
    private Button submitRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main);

        getRecipe = (Button) findViewById(R.id.get_recipe);

        getRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodMain.this, FoodManager.class);
                startActivity(i);
            }
        });

        submitRecipe = (Button) findViewById(R.id.submit_recipe);

        submitRecipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodMain.this, FoodForm.class);
                startActivity(i);
            }
        });

    }
}
