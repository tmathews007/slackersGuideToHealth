package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import static com.tommymathews.slackersguidetohealth.FoodActivity.removeShiftMode;

public class Events extends AppCompatActivity {

    private TextView mTextMessage;
    public ListView listViewEvents;
    public TextView dance, wellness, health;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        dance = (TextView) findViewById(R.id.text_dance);
        wellness = (TextView) findViewById(R.id.txt_wellness_desc);
        health = (TextView) findViewById(R.id.txt_mental);

        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Events.this, Danceathon.class));
                overridePendingTransition(0,0);
            }
        });

        wellness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Events.this, Wellness.class));
                overridePendingTransition(0,0);
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Events.this, Health.class));
                overridePendingTransition(0,0);
            }
        });
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById
                (R.id.bottomNavigationView);
        removeShiftMode(bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.exerciseItem) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.foodItem) {
                    //start the food activity
                    Intent i = new Intent(getApplicationContext(), FoodManager.class);
                    startActivity(i);
                    overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.exploreItem) {
                    Intent i = new Intent(getApplicationContext(), ExploreMain.class);
                    startActivity(i);
                    overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.funItem) {
                    Intent intent = new Intent(getApplicationContext(), Events.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                return false;
            }
        });
    }

}
