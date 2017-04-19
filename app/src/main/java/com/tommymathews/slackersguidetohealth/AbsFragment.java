package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AbsFragment extends android.support.v4.app.Fragment {

    private TextView absWorkout;

    public static AbsFragment newInstance() {
        AbsFragment fragment = new AbsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_abs, container, false);

        absWorkout = ( TextView ) view.findViewById( R.id.abs_workout );

        return view;
    }
}
