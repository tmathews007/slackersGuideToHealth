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
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by Ashwin on 4/4/2017.
 */

public class FoodFragment extends Fragment{
    //TODO get the activity here and redirect to the page
    // with the suggestions and recipe

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_food, container, false);

        Button nextPageButton = (Button) view.findViewById(R.id.nextPageFood);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);
        removeShiftMode(bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);


        nextPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText calories = (EditText) view.findViewById(R.id.calories);
                String cal = calories.getText().toString();
                int numCal = Integer.parseInt(cal);
                if(numCal<500){
                    //TODO Ashwin/Christine week of 4/18
                    Intent i = new Intent(getActivity(), FoodActivity2.class);
                    startActivity(i);
                }
                else if(numCal>=500 && numCal<750) {
                    Intent i = new Intent(getActivity(), FoodActivity.class);
                    startActivity(i);
                }
                else if(numCal>=750 && numCal<1000){
                    //TODO Ashwin/Christine week of 4/18
                    Intent i = new Intent(getActivity(), FoodActivity3.class);
                    startActivity(i);
                }
                else if(numCal>=1000 && numCal<=1500){
                    //TODO Ashwin/Christine week of 4/18
                    Intent i = new Intent(getActivity(), FoodActivity4.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Talk to a professional" +
                            " to develop a diet plan. Your calorie intake is too high.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /* Use this to switch between tabs. */
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.exerciseItem) {
                    Intent i = new Intent(getActivity(), FitnessActivity.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.foodItem) {
                } else if (item.getItemId() == R.id.exploreItem) {
                    Intent i = new Intent(getActivity(), ExploreMain.class);
                    startActivity(i);
                    getActivity().overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.funItem) {
                    Intent intent = new Intent(getActivity(), Events.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(0,0);
                }
                return false;
            }
        });

        return view;

    }
    static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }

}
