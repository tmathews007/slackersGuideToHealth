package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by Thomas on 4/11/2017.
 */

public class FitnessActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return FitnessFragment.newInstance();
    }
}
