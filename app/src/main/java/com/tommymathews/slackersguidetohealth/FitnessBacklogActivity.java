package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.tommymathews.slackersguidetohealth.model.Fitness;

public class FitnessBacklogActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return FitnessBacklogFragment.newInstance();
    }

}
