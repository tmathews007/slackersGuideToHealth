package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TricepsFragment extends Fragment {

    private TextView tricepsWorkout;

    public static TricepsFragment newInstance() {
        TricepsFragment fragment = new TricepsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_triceps, container, false);

        // The textview will contain a picture of the workout on the top along with the directions
        // on how to do the workout. The directions will be found with a string array from the
        // values package.
        tricepsWorkout = ( TextView ) view.findViewById( R.id.triceps_workout );

        return view;
    }
}
