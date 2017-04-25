package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.tommymathews.slackersguidetohealth.model.User;
import com.tommymathews.slackersguidetohealth.service.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 4/19/17.
 */

public class SQLiteUserService implements UserService {
    private SQLiteDatabase database;

    public SQLiteUserService(Context context) {
        database = new DbHelper(context).getWritableDatabase();
    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void addUser(User user) {
        ContentValues contentValues = getContentValues(user);
        User currUser = getUserByEmail(user.getEmail());
        if (currUser == null) {
            database.insert(DbSchema.UserTable.NAME, null, contentValues);
        } else {
            database.update(DbSchema.UserTable.NAME, contentValues,
                    DbSchema.UserTable.Columns.EMAIL + "=?", new String[]{currUser.getEmail()});
        }
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null)
            return null;

        List<User> users = queryUsers(DbSchema.UserTable.Columns.EMAIL,
                new String[]{email}, null);

        for (User user : users) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> prioritizedUser = queryUsers(null, null, null);
        return prioritizedUser;
    }

    private List<User> queryUsers(String whereClause, String[] whereArgs, String orderBy) {
        List<User> users = new ArrayList<User>();
        if (whereClause != null)
            whereClause = whereClause + "=?";

        Cursor cursor = database.query(DbSchema.UserTable.NAME, null,
                whereClause, whereArgs, null, null, orderBy);
        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                users.add(wrapper.getUser());
                wrapper.moveToNext();
            }
        } finally {
            cursor.close();
            wrapper.close();
        }

        return users;
    }

    private static ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbSchema.UserTable.Columns.NAME, user.getName());
        contentValues.put(DbSchema.UserTable.Columns.EMAIL, user.getEmail());
        contentValues.put(DbSchema.UserTable.Columns.PASSWORD, user.getPassword());
        contentValues.put(DbSchema.UserTable.Columns.GENDER, user.getGender().toString());
        contentValues.put(DbSchema.UserTable.Columns.AGE, user.getAge());
        contentValues.put(DbSchema.UserTable.Columns.WEIGHT, user.getWeight() + "");
        contentValues.put(DbSchema.UserTable.Columns.HEIGHT, user.getHeight() + "");
        contentValues.put(DbSchema.UserTable.Columns.FITNESS_GOAL, user.getFitnessGoal().toString());

        return contentValues;
    }

    private class UserCursorWrapper extends CursorWrapper {
        public UserCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public User getUser() {
            String name = getString(getColumnIndex(DbSchema.UserTable.Columns.NAME));
            String email = getString(getColumnIndex(DbSchema.UserTable.Columns.EMAIL));
            String password = getString(getColumnIndex(DbSchema.UserTable.Columns.PASSWORD));
            String gender = getString(getColumnIndex(DbSchema.UserTable.Columns.GENDER));
            int age = getInt(getColumnIndex(DbSchema.UserTable.Columns.AGE));
            int weight = getInt(getColumnIndex(DbSchema.UserTable.Columns.WEIGHT));
            int height = getInt(getColumnIndex(DbSchema.UserTable.Columns.HEIGHT));
            String fitnessGoal = getString(getColumnIndex(DbSchema.UserTable.Columns.FITNESS_GOAL));

            User user = new User(name, email, password, User.Gender.valueOf(gender),
                    age, weight, height, User.Goal.valueOf(fitnessGoal));

            return user;
        }
    }
}


