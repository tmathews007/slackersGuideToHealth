package com.tommymathews.slackersguidetohealth;

import android.support.v4.app.Fragment;

/**
 * Created by gregs on 5/6/2017.
 */

public class DisplayPlaylistsActivity extends ActivityFragmentWithMenu {

    @Override
    protected Fragment createFragment() {
        return DisplayPlaylistsFragment.newInstance();
    }


}
