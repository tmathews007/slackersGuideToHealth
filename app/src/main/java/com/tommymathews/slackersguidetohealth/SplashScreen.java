package com.tommymathews.slackersguidetohealth;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread myThread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(3000);
                    SharedPreferences sharedPreferences=getSharedPreferences(SignUp.LOGIN,MODE_WORLD_READABLE);

                    String unm=sharedPreferences.getString(SignUp.USERNAME, null);
                    if (unm != null) {
                        Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(newIntent);
                    } else {
                        Intent newIntent = new Intent(getApplicationContext(), MainActivity.class);
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