package com.tommymathews.slackersguidetohealth.service.impl;

import android.graphics.Bitmap;

/**
 * Created by cj on 4/19/17.
 */

public class DbSchema {
    public static final String EMAIL = "EMAIL";  //used in shared preferences
    public static final String LOGIN = "LOGIN";

    public static final class UserTable {
        public static final String USER_NAME = "USER_NAME";
        public static final class Columns {
            public static final String ID = "ID";
            public static final String NAME = "NAME";
            public static final String EMAIL = "EMAIL";
            public static final String PASSWORD = "PASSWORD";
            public static final String GENDER = "GENDER";
            public static final String AGE = "AGE";
            public static final String WEIGHT = "WEIGHT";
            public static final String HEIGHT = "HEIGHT";
            public static final String FITNESS_GOAL = "FITNESS_GOAL";
            public static final String FITNESS_PROGRESS = "FITNESS_PROGRESS";
            public static final String FOOD_PROGRESS = "FOOD_PROGRESS";
            public static final String EVENTS_PROGRESS = "EVENTS_PROGRESS";
        }
    }

    public static final class FoodTable {
        public static final String FOOD_NAME = "FOOD_NAME";
        public static final class Columns {
            public static final String ID = "ID";
            public static final String NAME = "NAME";
            public static final String RECOMMENDATION = "RECOMMENDATION";
            public static final String CALORIE_LEVEL = "CALORIE_LEVEL";
            public static final String INGREDIENTS = "INGREDIENTS";
            public static final String RECIPE = "RECIPE";
            public static final String IMAGE_PATH = "IMAGE_PATH";

        }
    }

    /**
     * The fitness table will contain five columns, namely:
     *      1. Fitness Name
     *      2. Body part that the workout focuses on
     *      3. Recommended number of reps
     *      4. Instructions
     *      5. IMAGEs to show what to do per step
     */
    public static final class FitnessTable {
        public static final String FITNESS_NAME = "FitnessList";
        public static final class Columns {
            public static final String ID = "ID";
            public static final String NAME = "NAME";
            public static final String LIKES = "LIKES";
            public static final String BODY_PART = "BODY_PART";
            public static final String NUM_REPS = "NUM_REPS";
            public static final String INSTRUCTIONS = "INSTRUCTIONS";
            public static final String STEPS = "STEPS";
            public static final String IMAGE = "IMAGE";
        }
    }


    public static final class StepsTable {
        public static final String STEPS_NAME = "STEPS";
        public static final class Columns {
            public static final String STEPN = "STEPN";
            public static final String STEP_1 = "STEP_1";
            public static final String STEP_2 = "STEP_2";
            public static final String STEP_3 = "STEP_3";
            public static final String STEP_4 = "STEP_4";
            public static final String STEP_5 = "STEP_5";
            public static final String STEP_6 = "STEP_6";
            public static final String STEP_7 = "STEP_7";
            public static final String STEP_8 = "STEP_9";
            public static final String STEP_9 = "STEP_8";
            public static final String STEP_10 = "STEP_10";
        }
    }

    public static final class ImagesTable {
        public static final String IMAGE_NAME = "IMAGES";
        public static final class Columns {
            public static final String IMAGEN = "Name";
            public static final String IMAGE_1 = "IMAGE_1";
            public static final String IMAGE_2 = "IMAGE_2";
            public static final String IMAGE_3 = "IMAGE_3";
            public static final String IMAGE_4 = "IMAGE_4";
            public static final String IMAGE_5 = "IMAGE_5";
            public static final String IMAGE_6 = "IMAGE_6";
            public static final String IMAGE_7 = "IMAGE_7";
            public static final String IMAGE_8 = "IMAGE_8";
            public static final String IMAGE_9 = "IMAGE_9";
            public static final String IMAGE_10 = "IMAGE_10";


        }
    }

    public static final class PlayListTable {
        public static final String PLAYLIST_NAME = "PLAYLISTS";
        public static final class Columns {
            public static final String ID = "ID";
            public static final String NAME = "NAME";
            public static final String LIKES = "LIKES";
            public static final String FITNESS_1 = "FITNESS_1";
            public static final String FITNESS_2 = "FITNESS_2";
            public static final String FITNESS_3 = "FITNESS_3";
            public static final String FITNESS_4 = "FITNESS_4";
            public static final String FITNESS_5 = "FITNESS_5";
            public static final String FITNESS_6 = "FITNESS_6";
            public static final String FITNESS_7 = "FITNESS_7";
            public static final String FITNESS_8 = "FITNESS_8";
            public static final String FITNESS_9 = "FITNESS_9";
            public static final String FITNESS_10 = "FITNESS_10";
        }
    }
}
