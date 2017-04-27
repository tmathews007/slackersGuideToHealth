package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuadsFragment extends Fragment {

    private TextView quadsWorkoutTitle;
    private TextView quadsWorkout;

    public static QuadsFragment newInstance() {
        QuadsFragment fragment = new QuadsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quads, container, false);

        // This will be the title of the page. The title will be the workout name.
        quadsWorkoutTitle = ( TextView ) view.findViewById( R.id.quads_workout_title );

//        // This will be where the workout directions are. It will be in the third textview, while
//        // the picture of the workout will be in the above textview.
//        quadsWorkout = ( TextView ) view.findViewById( R.id.quads_workout );
//        quadsWorkout.setText( R.array.quads_workout_array );

        return view;
    }
}
