package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.User;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

/**
 * Created by Ashley on 4/19/17.
 */

public class ProfileActivity extends Activity{
    private Button settingsButton;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private UserService userService;
    private TextView usernameView;
    private TextView genderView;
    private TextView ageView;
    private TextView heightView;
    private TextView weightView;
    private ImageView fitnessBadgeView;
    private ImageView foodBadgeView;
    private ImageView eventsBadgeView;
    private TextView fitnessPointsView;
    private TextView foodPointsView;
    private TextView eventsPointsView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);

        userService = DependencyFactory.getUserService(getApplicationContext());
        String email = sharedPreferences.getString(DbSchema.EMAIL,null);
        User user = userService.getUserByEmail(email);

        settingsButton = (Button) this.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
                finish();
            }
        });

        String username = user.getName();
        usernameView = (TextView) this.findViewById(R.id.username);
        usernameView.setText(username);

        User.Gender gender = user.getGender();
        genderView = (TextView) this.findViewById(R.id.gender);
        genderView.setText("Gender: " + gender.toString());

        int age = user.getAge();
        ageView = (TextView) this.findViewById(R.id.age);
        ageView.setText("Age: " + Integer.toString(age));

        int height = user.getHeight();
        heightView = (TextView) this.findViewById(R.id.height);
        heightView.setText("Height: " + user.convertHeight(height));

        int weight = user.getWeight();
        weightView = (TextView) this.findViewById(R.id.weight);
        weightView.setText("Weight: " + Integer.toString(weight) + "lbs.");

        progressBar = (ProgressBar) this.findViewById(R.id.exerciseProgress);
        progressBar.setVisibility(View.VISIBLE);

        progressStatus = user.getFitnessProgress() + user.getFoodProgress() + user.getEventsProgress();
        progressBar.setProgress(progressStatus);

        fitnessBadgeView = (ImageView) this.findViewById(R.id.fitness_badge);
        if(user.getFitnessProgress() >= 5){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fitness_badge);
            fitnessBadgeView.setImageBitmap(bitmap);
        }
        fitnessPointsView = (TextView) this.findViewById(R.id.fitness_points);
        fitnessPointsView.setText(Integer.toString(user.getFitnessProgress()) + "/5");

        foodBadgeView = (ImageView) this.findViewById(R.id.food_badge);
        if(user.getFoodProgress() >= 5){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.food_badge);
            foodBadgeView.setImageBitmap(bitmap);
        }
        foodPointsView = (TextView) this.findViewById(R.id.food_points);
        foodPointsView.setText(Integer.toString(user.getFoodProgress()) + "/5");

        eventsBadgeView = (ImageView) this.findViewById(R.id.events_badge);
        if(user.getEventsProgress() >= 5){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.events_badge);
            eventsBadgeView.setImageBitmap(bitmap);
        }
        eventsPointsView = (TextView) this.findViewById(R.id.events_points);
        eventsPointsView.setText(Integer.toString(user.getEventsProgress()) + "/5");
    }
}
