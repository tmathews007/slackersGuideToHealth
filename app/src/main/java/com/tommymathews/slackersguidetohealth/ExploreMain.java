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

/**
 * Created by lidya on 4/6/2017.
 */

public class ExploreMain extends AppCompatActivity {
    private TextView mTextMessage;
    public ListView listViewEvents;
    public TextView eat, events;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);


        eat = (TextView) findViewById(R.id.textView);

        //events = (TextView) findViewById(R.id.textView2);




        eat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExploreMain.this,ExploreEatOutRight.class));
                overridePendingTransition(0,0);
            }
        });

        /*events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExploreMain.this,Events.class));
                overridePendingTransition(0,0);
            }
        });*/




        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById
                (R.id.bottomNavigationView);
        removeShiftMode(bottomNavigationView);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.exerciseItem) {
                    Intent intent = new Intent(getApplicationContext(), FitnessActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                } else if (item.getItemId() == R.id.foodItem) {
                    //start the food activity
                    Intent i = new Intent(getApplicationContext(), FoodManager.class);
                    startActivity(i);
                    overridePendingTransition(0,0);

                } else if (item.getItemId() == R.id.exploreItem) {
                } else if (item.getItemId() == R.id.funItem) {
                    ///Intent intent = new Intent(getApplicationContext(), Events.class);
                    //startActivity(intent);
                    overridePendingTransition(0,0);
                }
                return false;
            }
        });
    }

}
