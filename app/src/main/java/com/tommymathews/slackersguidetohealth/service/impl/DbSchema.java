package com.tommymathews.slackersguidetohealth.service.impl;

/**
 * Created by cj on 4/19/17.
 */

public class DbSchema {
    public static final String EMAIL = "EMAIL";  //used in shared preferences
    public static final String LOGIN = "LOGIN";

    public static final class UserTable {
        public static final String NAME = "NAME";
        public static final class Columns {
            public static final String NAME = "NAME";
            public static final String EMAIL = "EMAIL";
            public static final String PASSWORD = "PASSWORD";
            public static final String GENDER = "GENDER";
            public static final String AGE = "AGE";
            public static final String WEIGHT = "WEIGHT";
            public static final String HEIGHT = "HEIGHT";
            public static final String FITNESS_GOAL = "FITNESS_GOAL";
        }
    }

    public static final class FoodTable {
        public static final String NAME = "NAME";
        public static final class Columns {
            public static final String NAME = "NAME";
            public static final String CALORIE_LEVEL = "CALORIE_LEVEL";
            public static final String INGREDIENTS = "INGREDIENTS";
            public static final String RECIPE = "RECIPE";
        }
    }

    /**
     * The fitness table will contain five columns, namely:
     *      1. Fitness Name
     *      2. Body part that the workout focuses on
     *      3. Recommended number of reps
     *      4. Instructions
     *      5. Images to show what to do per step
     */
    public static final class FitnessTable {
        public static final String FITNESS_NAME = "NAME";
        public static final class Columns {
            public static final String BODY_PART = "BODY_PART";
            public static final String NUM_REPS = "NUM_REPS";
            public static final String INSTRUCTIONS = "INSTRUCTIONS";
            public static final String IMAGE = "IMAGE";
        }
    }
}
