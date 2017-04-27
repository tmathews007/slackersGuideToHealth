package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by gregs on 4/26/2017.
 */

public class FitnessFinishedActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return FitnessFinishedFragment.newInstance();
    }
}
