package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "slackers_guide.db";
    private static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbSchema.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbSchema.UserTable.Columns.NAME + ", " +
                DbSchema.UserTable.Columns.EMAIL + ", " +
                DbSchema.UserTable.Columns.PASSWORD + ", " +
                DbSchema.UserTable.Columns.GENDER + ", " +
                DbSchema.UserTable.Columns.AGE + ", " +
                DbSchema.UserTable.Columns.WEIGHT + ", " +
                DbSchema.UserTable.Columns.HEIGHT + ", " +
                DbSchema.UserTable.Columns.FITNESS_GOAL + ")"
        );

        db.execSQL("create table " + DbSchema.FoodTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbSchema.FoodTable.Columns.NAME + ", " +
                DbSchema.FoodTable.Columns.CALORIE_LEVEL + ", " +
                DbSchema.FoodTable.Columns.INGREDIENTS + ", " +
                DbSchema.FoodTable.Columns.RECIPE + "," +
                DbSchema.FoodTable.Columns.IMAGE + ")"
        );

        /**
         * This is the fitness table that sets the various parts of the table for the database.
         */
        db.execSQL( "create table " + DbSchema.FitnessTable.FITNESS_NAME + "( " +
                " _id integer primary key autoincrement, " +
                DbSchema.FitnessTable.Columns.BODY_PART + ", " +
                DbSchema.FitnessTable.Columns.NUM_REPS + ", " +
                DbSchema.FitnessTable.Columns.INSTRUCTIONS + ", " +
                DbSchema.FitnessTable.Columns.IMAGE +
                " )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}