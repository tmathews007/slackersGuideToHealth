package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Thomas on 4/12/2017.
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
        //if user clicks on abs, go to abs fragment
        //else if user clicks on back, go to back fragment
        //else if user clicks on biceps, go to biceps fragment
        //...
        //else go to triceps fragment
    }
}
