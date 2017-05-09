package com.tommymathews.slackersguidetohealth;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.tommymathews.slackersguidetohealth.model.Food;
import com.tommymathews.slackersguidetohealth.service.FoodService;

import java.util.List;

/**
 * Created by Ashwin on 4/4/2017.
 */

public class FoodFragment extends Fragment{
    //TODO get the activity here and redirect to the page
    // with the suggestions and recipe

    ProgressDialog dialog;

    public static final String CALORIES = "CALORIES";
    public static final String FOOD = "FOOD";

    private Button nextPageButton;
    private EditText calories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_get_food_recipe, container, false);

        nextPageButton = (Button) view.findViewById(R.id.nextPageFood);
        calories = (EditText) view.findViewById(R.id.calories);

        nextPageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String cal = calories.getText().toString();
                int numCal = Integer.parseInt(cal);
                int low = 0, high = 0;

                if(numCal < 500){
                    high = 500;
                } else if(numCal>=500 && numCal<750) {
                    low = 500;
                    high = 750;
                } else if(numCal>=750 && numCal<1000){
                    low = 750;
                    high = 1000;
                } else if(numCal>=1000 && numCal<=1500){
                    low = 1000;
                    high = 1500;
                } else {
                    low = -1;
                }

                if (low != -1)
                    new ReadFromFoodDB().execute(low,high);
                else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("High Calorie Count").setMessage("This is not the app for you.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }).create().show();
                }

            }
        });

        return view;

    }

    private void passInFood(List<Food> foods) {
        if (foods == null || foods.isEmpty()) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("No Recipes!").setMessage("We couldn't find a recipe fitting your specifications! Do you want to try " +
                    "to find something else or view a random recipe?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getActivity(), FoodRecipesActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }).create().show();
        } else {
            Food food = foods.get((int)(Math. random() * foods.size()));
            Intent i = new Intent(getActivity(), FoodRecipesActivity.class);
            i.putExtra(FOOD, food);
            startActivity(i);
        }
    }

    @Override
    public void onPause(){

        super.onPause();
        if(dialog != null)
            dialog.dismiss();
    }

    private class ReadFromFoodDB extends AsyncTask<Integer, Void, List<Food>> {

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Getting recipe!");
            dialog.show();
        }

        @Override
        protected List<Food> doInBackground(Integer... params) {
            FoodService foodService = DependencyFactory.getFoodService(getActivity());
            return foodService.getFoodByCalorieRange(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(List<Food> foods) {
            super.onPostExecute(foods);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            passInFood(foods);
        }
    }

}