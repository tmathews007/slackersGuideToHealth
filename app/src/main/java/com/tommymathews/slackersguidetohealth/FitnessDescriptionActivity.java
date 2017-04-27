package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by gregs on 4/26/2017.
 */

public class FitnessDescriptionActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return FitnessDescriptionFragment.newInstance();
    }
}
