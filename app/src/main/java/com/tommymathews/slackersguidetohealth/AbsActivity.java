package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

public class AbsActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return AbsFragment.newInstance();
    }
}
