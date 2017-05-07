package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tommymathews.slackersguidetohealth.model.Fitness;

public class FitnessPlaylistBacklogActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return FitnessPlaylistBacklogFragment.newInstance();
    }

}
