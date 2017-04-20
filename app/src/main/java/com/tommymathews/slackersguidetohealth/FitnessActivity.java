package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Thomas on 4/12/2017.
 */

public class FitnessActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return FitnessFragment.newInstance();
    }
}
