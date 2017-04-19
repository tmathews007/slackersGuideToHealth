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
            database.insert(UserSchema.UserTable.NAME, null, contentValues);
        } else {
            database.update(UserSchema.UserTable.NAME, contentValues,
                    UserSchema.UserTable.Columns.EMAIL + "=?", new String[]{currUser.getEmail()});
        }
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null)
            return null;

        List<User> users = queryUsers(UserSchema.UserTable.Columns.EMAIL,
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

        Cursor cursor = database.query(UserSchema.UserTable.NAME, null,
                whereClause, whereArgs, null, null, orderBy);
        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                users.add(wrapper.getUser());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        return users;
    }

    private static ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserSchema.UserTable.Columns.EMAIL, user.getEmail());
        contentValues.put(UserSchema.UserTable.Columns.PASSWORD, user.getPassword());
        contentValues.put(UserSchema.UserTable.Columns.GENDER, user.getGender().toString());
        contentValues.put(UserSchema.UserTable.Columns.AGE, user.getAge());
        contentValues.put(UserSchema.UserTable.Columns.WEIGHT, user.getWeight() + "");
        contentValues.put(UserSchema.UserTable.Columns.HEIGHT, user.getHeight() + "");
        contentValues.put(UserSchema.UserTable.Columns.FITNESS_GOAL, user.getFitnessGoal().toString());

        return contentValues;
    }

    private class UserCursorWrapper extends CursorWrapper {
        public UserCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public User getUser() {
            String email = getString(getColumnIndex(UserSchema.UserTable.Columns.EMAIL));
            String password = getString(getColumnIndex(UserSchema.UserTable.Columns.PASSWORD));
            String gender = getString(getColumnIndex(UserSchema.UserTable.Columns.GENDER));
            int age = getInt(getColumnIndex(UserSchema.UserTable.Columns.AGE));
            int weight = getInt(getColumnIndex(UserSchema.UserTable.Columns.WEIGHT));
            int height = getInt(getColumnIndex(UserSchema.UserTable.Columns.HEIGHT));
            String fitnessGoal = getString(getColumnIndex(UserSchema.UserTable.Columns.FITNESS_GOAL));

            User user = new User(email, password, User.Gender.valueOf(gender),
                    age, weight, height, User.Goal.valueOf(fitnessGoal));

            return user;
        }
    }
}


