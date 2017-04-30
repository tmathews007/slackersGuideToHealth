package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tommymathews.slackersguidetohealth.model.Fitness;

public class FitnessQuestionsActivity extends ActivityFragmentWithMenu {

    private final String TAG = getClass().getSimpleName();

    private static final String EXTRA_FITNESS_ID = "FITNESS_ID";

    @Override
    protected Fragment createFragment() {
        return FitnessQuestionsFragment.newInstance( EXTRA_FITNESS_ID );
    }

    public static Intent newIntent(Context context, String storyId) {
        Intent intent = new Intent(context, FitnessBacklogActivity.class);
        intent.putExtra(EXTRA_FITNESS_ID, storyId);
        return intent;
    }

    public static Fitness getFitnessCreated(Intent data) {
        return FitnessQuestionsFragment.getFitnessCreated(data);
    }
}
