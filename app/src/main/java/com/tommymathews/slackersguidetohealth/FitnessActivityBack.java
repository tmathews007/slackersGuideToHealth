package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by gregs on 4/19/2017.
 */

public class FitnessActivityBack extends ActivityFragmentWithMenu{
    @Override
    protected Fragment createFragment() {
        return FitnessFragmentBack.newInstance();
    }
}
