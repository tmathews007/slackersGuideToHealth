package com.tommymathews.slackersguidetohealth.service.impl;

/**
 * Created by cj on 4/19/17.
 */

public class UserSchema {
    public static final String EMAIL = "EMAIL";  //used in shared preferences
    public static final String LOGIN = "LOGIN";

    public static final class UserTable {
        public static final String NAME = "NAME";
        public static final class Columns {
            public static final String EMAIL = "EMAIL";
            public static final String PASSWORD = "PASSWORD";
            public static final String GENDER = "GENDER";
            public static final String AGE = "AGE";
            public static final String WEIGHT = "WEIGHT";
            public static final String HEIGHT = "HEIGHT";
            public static final String FITNESS_GOAL = "FITNESS_GOAL";
        }

    }
}
