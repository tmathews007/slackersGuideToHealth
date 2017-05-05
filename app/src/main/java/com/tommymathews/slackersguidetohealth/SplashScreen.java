package com.tommymathews.slackersguidetohealth;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2500);
                    SharedPreferences sharedPreferences=getSharedPreferences(DbSchema.LOGIN,MODE_PRIVATE);

                    String username=sharedPreferences.getString(DbSchema.EMAIL, null);
                    if (username != null) {
                        Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(newIntent);
                    } else {
                        Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(newIntent);
                    }

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        };
        myThread.start();
    }
}