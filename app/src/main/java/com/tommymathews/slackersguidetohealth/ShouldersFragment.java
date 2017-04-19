package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Thomas on 4/18/2017.
 */

public class ShouldersFragment extends Fragment {

    private TextView shouldersWorkout;

    public static ShouldersFragment newInstance() {
        ShouldersFragment fragment = new ShouldersFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoulders, container, false);

        shouldersWorkout = ( TextView ) view.findViewById( R.id.shoulders_workout );

        return view;
    }

}
