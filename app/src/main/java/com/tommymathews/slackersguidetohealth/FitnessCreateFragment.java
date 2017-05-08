package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tommymathews.slackersguidetohealth.model.Fitness;

/**
 * Created by gregs on 5/8/2017.
 */

public class FitnessCreateFragment extends Fragment {
    private Fitness fitness;

    EditText fitnessTitle;
    EditText fitnessDescription;


    public static FitnessBacklogFragment newInstance() {
        FitnessBacklogFragment fragment = new FitnessBacklogFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_backlog, container, false);

        fitnessTitle = (EditText)view.findViewById(R.id.fitness_edit_name);
        if (fitness != null) {
           fitnessTitle.setText(fitness.getFitnessName());
        }

        fitnessDescription = (EditText)view.findViewById(R.id.fitness_edit_description);
        if (fitness != null) {
            fitnessTitle.setText(fitness.getFitnessName());
        }
        return view;
    }
}
