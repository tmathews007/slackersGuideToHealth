package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

public class QuadsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return QuadsFragment.newInstance();
    }
}
