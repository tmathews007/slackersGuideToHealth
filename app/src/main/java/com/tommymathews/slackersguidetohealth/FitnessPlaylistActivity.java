package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.model.Playlist;

public class FitnessPlaylistActivity extends ActivityFragmentWithMenu {

    private final String TAG = getClass().getSimpleName();

    private static final String EXTRA_FITNESS_ID = "FITNESS_ID";

    @Override
    protected Fragment createFragment() {
        return FitnessPlaylistFragment.newInstance( EXTRA_FITNESS_ID );
    }

    public static Intent newIntent(Context context, String storyId) {
        Intent intent = new Intent(context, FitnessBacklogActivity.class);
        intent.putExtra(EXTRA_FITNESS_ID, storyId);
        return intent;
    }

    public static Playlist getFitnessCreated(Intent data) {
        return FitnessPlaylistFragment.getFitnessCreated(data);
    }
}
