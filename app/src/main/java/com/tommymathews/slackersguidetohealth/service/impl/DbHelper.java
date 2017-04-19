package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cj on 4/19/17.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "slackers_guide.db";
    private static final int VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserSchema.UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserSchema.UserTable.Columns.EMAIL + ", " +
                UserSchema.UserTable.Columns.PASSWORD + ", " +
                UserSchema.UserTable.Columns.GENDER + ", " +
                UserSchema.UserTable.Columns.AGE + ", " +
                UserSchema.UserTable.Columns.WEIGHT + ", " +
                UserSchema.UserTable.Columns.HEIGHT + ", " +
                UserSchema.UserTable.Columns.FITNESS_GOAL + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}