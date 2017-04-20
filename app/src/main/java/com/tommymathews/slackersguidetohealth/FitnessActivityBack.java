package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by gregs on 4/19/2017.
 */

public class FitnessActivityBack extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return FitnessFragmentBack.newInstance();
    }
}
