package com.tommymathews.slackersguidetohealth;

import android.content.Context;

import com.tommymathews.slackersguidetohealth.service.FoodService;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLiteFoodService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLiteUserService;

/**
 * Created by cj on 4/19/17.
 */

public class DependencyFactory {
    private static UserService userService;
    private static FoodService foodService;

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

}
