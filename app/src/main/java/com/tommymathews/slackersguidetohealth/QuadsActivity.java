package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

public class QuadsActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return QuadsFragment.newInstance();
    }
}
