package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;

public class FoodForm extends ActivityWithMenu {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_form);
    }
}
