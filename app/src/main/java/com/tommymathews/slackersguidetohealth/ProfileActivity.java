package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.User;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

/**
 * Created by Ashley on 4/19/17.
 */

public class ProfileActivity extends Activity{
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private UserService userService;
    private TextView usernameView;
    private TextView genderView;
    private TextView ageView;
    private TextView heightView;
    private TextView weightView;
    private TextView fitnessGoalView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);

        userService = DependencyFactory.getUserService(getApplicationContext());
        String email = sharedPreferences.getString(DbSchema.EMAIL,null);
        User user = userService.getUserByEmail(email);

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
        heightView.setText("Height: " + Integer.toString(height));

        int weight = user.getWeight();
        weightView = (TextView) this.findViewById(R.id.weight);
        weightView.setText("Weight: " + Integer.toString(weight));

        User.Goal goal = user.getFitnessGoal();
        fitnessGoalView = (TextView) this.findViewById(R.id.fitness_goal);
        fitnessGoalView.setText("Goal: " + goal.toString());

        progressBar = (ProgressBar) this.findViewById(R.id.exerciseProgress);
        progressBar.setVisibility(View.VISIBLE);

        progressStatus = 50;
        progressBar.setProgress(progressStatus);
    }
}
