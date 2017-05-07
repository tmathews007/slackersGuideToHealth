package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//database that contains a table for users, foods, and fitness ideas
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "slackers_guide.db";
    private static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbSchema.UserTable.USER_NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbSchema.UserTable.Columns.ID + ", " +
                DbSchema.UserTable.Columns.NAME + ", " +
                DbSchema.UserTable.Columns.EMAIL + ", " +
                DbSchema.UserTable.Columns.PASSWORD + ", " +
                DbSchema.UserTable.Columns.GENDER + ", " +
                DbSchema.UserTable.Columns.AGE + ", " +
                DbSchema.UserTable.Columns.WEIGHT + ", " +
                DbSchema.UserTable.Columns.HEIGHT + ", " +
                DbSchema.UserTable.Columns.FITNESS_GOAL + ", " +
                DbSchema.UserTable.Columns.FITNESS_PROGRESS + ", " +
                DbSchema.UserTable.Columns.FOOD_PROGRESS + ", " +
                DbSchema.UserTable.Columns.EVENTS_PROGRESS + ")"
        );

        db.execSQL("create table " + DbSchema.FoodTable.FOOD_NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbSchema.FoodTable.Columns.ID + ", " +
                DbSchema.FoodTable.Columns.NAME + ", " +
                DbSchema.FoodTable.Columns.RECOMMENDATION + ", " +
                DbSchema.FoodTable.Columns.CALORIE_LEVEL + ", " +
                DbSchema.FoodTable.Columns.INGREDIENTS + ", " +
                DbSchema.FoodTable.Columns.RECIPE + "," +
                DbSchema.FoodTable.Columns.IMAGE_PATH + ")"
        );

        db.execSQL( "create table " + DbSchema.StepsTable.STEPS_NAME + "( " +
                " _id integer primary key autoincrement, " +
                DbSchema.StepsTable.Columns.STEPN + "," +
                DbSchema.StepsTable.Columns.STEP_1 + "," +
                DbSchema.StepsTable.Columns.STEP_2 + "," +
                DbSchema.StepsTable.Columns.STEP_3 + "," +
                DbSchema.StepsTable.Columns.STEP_4 + "," +
                DbSchema.StepsTable.Columns.STEP_5 + "," +
                DbSchema.StepsTable.Columns.STEP_6 + "," +
                DbSchema.StepsTable.Columns.STEP_7 + "," +
                DbSchema.StepsTable.Columns.STEP_8 + "," +
                DbSchema.StepsTable.Columns.STEP_9 + "," +
                DbSchema.StepsTable.Columns.STEP_10 +
                " )"
        );

        db.execSQL( "create table " + DbSchema.ImagesTable.IMAGE_NAME + "( " +
                " _id integer primary key autoincrement, " +
                DbSchema.ImagesTable.Columns.IMAGEN + "," +
                DbSchema.ImagesTable.Columns.IMAGE_1 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_2 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_3 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_4 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_5 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_6 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_7 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_8 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_9 + "," +
                DbSchema.ImagesTable.Columns.IMAGE_10 +
                " )"
        );


        /**
         * This is the fitness table that sets the various parts of the table for the database.
         */
        db.execSQL( "create table " + DbSchema.FitnessTable.FITNESS_NAME + "( " +
                " _id integer primary key autoincrement, " +
                DbSchema.FitnessTable.Columns.ID + ", " +
                DbSchema.FitnessTable.Columns.NAME + ", " +
                DbSchema.FitnessTable.Columns.LIKES + ", " +
                DbSchema.FitnessTable.Columns.BODY_PART + ", " +
                DbSchema.FitnessTable.Columns.NUM_REPS + ", " +
                DbSchema.FitnessTable.Columns.INSTRUCTIONS + ", " +
                DbSchema.FitnessTable.Columns.STEPS + ", " +
                DbSchema.FitnessTable.Columns.IMAGE +
                " )"
        );

        db.execSQL( "create table " + DbSchema.PlayListTable.PLAYLIST_NAME + "( " +
                " _id integer primary key autoincrement, " +
                DbSchema.PlayListTable.Columns.ID + ", " +
                DbSchema.PlayListTable.Columns.NAME + ", " +
                DbSchema.PlayListTable.Columns.LIKES + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_1 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_2 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_3 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_4 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_5 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_6 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_7 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_8 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_9 + ", " +
                DbSchema.PlayListTable.Columns.FITNESS_10 +
                " )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}