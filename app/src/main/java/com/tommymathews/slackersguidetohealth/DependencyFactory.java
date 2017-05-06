package com.tommymathews.slackersguidetohealth;

import android.content.Context;
import android.util.Log;

import com.tommymathews.slackersguidetohealth.service.FoodService;
import com.tommymathews.slackersguidetohealth.service.FitnessService;
import com.tommymathews.slackersguidetohealth.service.PlaylistService;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLiteFitnessService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLiteFoodService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLitePlaylistService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLiteUserService;

public class DependencyFactory {
    private static UserService userService;
    private static FoodService foodService;
    private static FitnessService fitnessService;
    private static PlaylistService playlistService;

    public static UserService getUserService(Context context) {
        if (userService == null) {
            userService = new SQLiteUserService(context);
        }
        return userService;
    }

    public static FoodService getFoodService(Context context) {
        if (foodService == null) {
            foodService = new SQLiteFoodService(context);
        }
        return foodService;
    }

    public static FitnessService getFitnessService(Context context) {
        if (fitnessService == null) {
            fitnessService = new SQLiteFitnessService(context);
        }
        return fitnessService;
    }

    public static PlaylistService getPlaylistService(Context context) {
        if (playlistService == null) {
            playlistService = new SQLitePlaylistService(context);
        }
        return playlistService;
    }

}
