package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

public class AbsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return AbsFragment.newInstance();
    }
}
