package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tommymathews.slackersguidetohealth.model.Fitness;

/**
 * Created by gregs on 4/26/2017.
 */

public class FitnessDescriptionActivity extends ActivityFragmentWithMenu {
    private static final String EXTRA_PLAYLIST_ID = "PLAYLIST_ID";

    @Override
    protected Fragment createFragment() {
        return FitnessDescriptionFragment.newInstance();
    }

    public static Intent newIntent(Context context, String playlistId) {
        Intent intent = new Intent(context, FitnessDescriptionActivity.class);
        intent.putExtra(EXTRA_PLAYLIST_ID, playlistId);
        return intent;
    }

    public static Fitness getStoryCreated(Intent data) {
        return FitnessDescriptionFragment.getFitnessCreated(data);
    }
}
