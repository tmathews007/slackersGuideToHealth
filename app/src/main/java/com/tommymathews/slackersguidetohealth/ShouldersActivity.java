package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

public class ShouldersActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return ShouldersFragment.newInstance();
    }
}
