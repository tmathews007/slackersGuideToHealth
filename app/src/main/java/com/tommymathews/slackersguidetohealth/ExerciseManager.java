package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Ashwin on 4/12/2017.
 */

public class ExerciseManager extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_manager);

        //Tommy and Greg will complete the if statements below.
        //Depending on what part of the drawable that the user
        //clicks on, this class will create a new fragment
        //for the selected fitness group.


        //button/etc areas here;
        //if user clicks on abs, setOnClickListener{abs fragment}
        //else if user clicks on back, setOnClickListener{back fragment}
        //else if user clicks on biceps, setOnClickListener{biceps fragment}
        //...
        //else setOnClickListener{triceps fragment}
    }
}
