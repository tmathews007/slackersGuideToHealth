package com.tommymathews.slackersguidetohealth;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Thomas on 4/12/2017.
 */

public class FitnessActivity extends ActivityFragmentWithMenu {
    @Override
    protected Fragment createFragment() {
        return FitnessFragment.newInstance();
    }
}
