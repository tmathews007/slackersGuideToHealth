package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

public class TricepsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return TricepsFragment.newInstance();
    }
}
