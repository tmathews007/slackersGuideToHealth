package com.tommymathews.slackersguidetohealth;

import android.content.Context;

import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.SQLiteUserService;

/**
 * Created by cj on 4/19/17.
 */

public class DependencyFactory {
    private static UserService userService;

    public static UserService getUserService(Context context) {
        if (userService == null) {
            //storyService = new InMemoryStoryService(context);
            userService = new SQLiteUserService(context);
        }
        return userService;
    }
}
