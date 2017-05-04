package com.tommymathews.slackersguidetohealth;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gregs on 4/26/2017.
 */

public class FitnessFinishedFragment extends Fragment{
    private UserService userService;

    public static Fragment newInstance() {
        Fragment fragment = new FitnessFinishedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.finished_fitness, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);

        userService = DependencyFactory.getUserService(getActivity().getApplicationContext());
        String email = sharedPreferences.getString(DbSchema.EMAIL,null);
        userService.incrementFitnessProgress(email);

        return view;
    }
}
