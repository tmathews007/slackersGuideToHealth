package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by gregs on 5/8/2017.
 */

public class FitnessCreateActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return FitnessCreateFragment.newInstance();
    }

}
