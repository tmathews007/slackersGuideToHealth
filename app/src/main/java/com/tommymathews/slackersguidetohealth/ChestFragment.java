package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChestFragment extends Fragment {

    private TextView chestWorkout;

    public static ChestFragment newInstance() {
        ChestFragment fragment = new ChestFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_chest, container, false );

        // The textview will contain a picture of the workout on the top along with the directions
        // on how to do the workout. The directions will be found with a string array from the
        // values package.
        chestWorkout = ( TextView ) view.findViewById( R.id.chest_workout );

        return view;
    }
}
