package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ashwin on 4/25/2017.
 */

public class Food extends AppCompatActivity{
    public int calorieLevel=0;
    public String recipe="";
    public String name="";
    public String ingredients="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Food(int calLevel, String r, String n, String i){
        calorieLevel=calLevel;
        recipe=r;
        name=n;
        ingredients=i;
    }
}
