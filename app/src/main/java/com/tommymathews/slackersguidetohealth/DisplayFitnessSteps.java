package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by gregs on 4/26/2017.
 */

public class DisplayFitnessSteps extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return DisplayFitnessStepsFragment.newInstance();
    }
}
