package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Ashley on 4/19/17.
 */

public class ProfileActivity extends Activity{
    private ProgressBar progressBar;
    private int progressStatus = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_profile);

        progressBar = (ProgressBar) this.findViewById(R.id.exerciseProgress);
        progressBar.setVisibility(View.VISIBLE);

        progressStatus = 50;
        progressBar.setProgress(progressStatus);
    }
}
