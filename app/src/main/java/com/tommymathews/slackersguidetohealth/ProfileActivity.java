package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.User;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import java.io.File;

/**
 * Created by Ashley on 4/19/17.
 */

public class ProfileActivity extends AppCompatActivity{
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
    private ImageButton profilePicButton;
    private Button logoutButton;
    private Button settingsButton;
    private File photoFile;
    private static final int REQUEST_PHOTO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);

        userService = DependencyFactory.getUserService(getApplicationContext());
        String email = sharedPreferences.getString(DbSchema.EMAIL,null);
        User user = userService.getUserByEmail(email);

        logoutButton = (Button) findViewById(R.id.logOutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences(DbSchema.LOGIN, 0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });

        settingsButton = (Button) this.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
                finish();
            }
        });

        profilePicButton = (ImageButton) this.findViewById(R.id.profilePic);
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null){
                    photoFile = getPhotoFile();
                    if (photoFile != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(intent, REQUEST_PHOTO);
                    } else {
                        profilePicButton.setEnabled(false);
                    }
                } else {
                    profilePicButton.setEnabled(false);
                }
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

    private File getPhotoFile(){
        File externalPhotoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalPhotoDir == null){
            return null;
        }

        return new File(externalPhotoDir, "IMG_" + System.currentTimeMillis() + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            profilePicButton.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menuItem) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation_profile, menuItem);
        return super.onCreateOptionsMenu(menuItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsItem:
                Intent intent = new Intent(ProfileActivity.this, Settings.class);
                startActivity(intent);
                return true;
            case R.id.exerciseItem:
                intent = new Intent(getApplicationContext(), FitnessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.foodItem:
                intent = new Intent(getApplicationContext(), FoodMain.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.funItem:
                intent = new Intent(getApplicationContext(), Events.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.mainItem:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
