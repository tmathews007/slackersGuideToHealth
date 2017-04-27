package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class Events extends ActivityWithMenu {

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
                startActivity(new Intent(Events.this, PhysicalHealth.class));
                overridePendingTransition(0,0);
            }
        });
    }

}
