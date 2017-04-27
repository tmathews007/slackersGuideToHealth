package com.tommymathews.slackersguidetohealth;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * Created by Ashwin on 4/4/2017.
 */

public class FoodFragment extends Fragment{
    //TODO get the activity here and redirect to the page
    // with the suggestions and recipe

    public final String CALORIES = "CALORIES";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_food, container, false);

        Button nextPageButton = (Button) view.findViewById(R.id.nextPageFood);

        nextPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText calories = (EditText) view.findViewById(R.id.calories);
                String cal = calories.getText().toString();
                int numCal = Integer.parseInt(cal);
//                if(numCal<500){
                    //TODO Ashwin/Christine week of 4/18
                    Intent i = new Intent(getActivity(), FoodActivity2.class);
                    i.putExtra(CALORIES, numCal);
                    startActivity(i);
//                }
//                else if(numCal>=500 && numCal<750) {
//                    Intent i = new Intent(getActivity(), FoodActivity.class);
//                    startActivity(i);
//                }
//                else if(numCal>=750 && numCal<1000){
//                    //TODO Ashwin/Christine week of 4/18
//                    Intent i = new Intent(getActivity(), FoodActivity3.class);
//                    startActivity(i);
//                }
//                else if(numCal>=1000 && numCal<=1500){
//                    //TODO Ashwin/Christine week of 4/18
//                    Intent i = new Intent(getActivity(), FoodActivity4.class);
//                    startActivity(i);
//                }
//                else{
//                    Toast.makeText(getActivity().getApplicationContext(), "Talk to a professional" +
//                            " to develop a diet plan. Your calorie intake is too high.", Toast.LENGTH_SHORT).show();
//                }
            }
        });

        return view;

    }
}
