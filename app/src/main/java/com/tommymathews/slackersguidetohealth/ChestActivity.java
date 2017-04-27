package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by Thomas on 4/12/2017.
 */

public class ChestActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return ChestFragment.newInstance();
    }
}
