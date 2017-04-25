package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.tommymathews.slackersguidetohealth.model.Food;
import com.tommymathews.slackersguidetohealth.service.FoodService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cj on 4/19/17.
 */

public class SQLiteFoodService implements FoodService {
    private SQLiteDatabase database;

    public SQLiteFoodService(Context context) {
        database = new DbHelper(context).getWritableDatabase();
    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }


    @Override
    public Food getFoodByName(String food) {
        if (food == null)
            return null;

        List<Food> allFoods = queryFood(DbSchema.FoodTable.Columns.NAME,
                new String[]{food}, null);

        for (Food yum : allFoods) {
            if (yum.getName().equals(food))
                return yum;
        }
        return null;
    }

    @Override
    public List<Food> getAllFoods() {
        List<Food> foods = queryFood(null, null, null);
        return foods;
    }

    private List<Food> queryFood(String whereClause, String[] whereArgs, String orderBy) {
        List<Food> foods = new ArrayList<Food>();
        if (whereClause != null)
            whereClause = whereClause + "=?";

        Cursor cursor = database.query(DbSchema.FoodTable.NAME, null,
                whereClause, whereArgs, null, null, orderBy);
        FoodCursorWrapper wrapper = new FoodCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                foods.add(wrapper.getFood());
                wrapper.moveToNext();
            }
        } finally {
            cursor.close();
            wrapper.close();
        }

        return foods;
    }

    private static ContentValues getContentValues(Food food) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbSchema.FoodTable.Columns.NAME, food.getName());
        contentValues.put(DbSchema.FoodTable.Columns.CALORIE_LEVEL, food.getCalorieLevel());
        contentValues.put(DbSchema.FoodTable.Columns.INGREDIENTS, food.getIngredients());
        contentValues.put(DbSchema.FoodTable.Columns.RECIPE, food.getRecipe());

        return contentValues;
    }

    @Override
    public void addFood(Food food) {
        ContentValues contentValues = getContentValues(food);
        Food currFood = getFoodByName(food.getName());
        if (currFood == null) {
            database.insert(DbSchema.FoodTable.NAME, null, contentValues);
        } else {
            database.update(DbSchema.FoodTable.NAME, contentValues,
                    DbSchema.FoodTable.Columns.NAME + "=?", new String[]{currFood.getName()});
        }
    }

    private class FoodCursorWrapper extends CursorWrapper {
        public FoodCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Food getFood() {
            String name = getString(getColumnIndex(DbSchema.FoodTable.Columns.NAME));
            int calories = getInt(getColumnIndex(DbSchema.FoodTable.Columns.CALORIE_LEVEL));
            String recipe = getString(getColumnIndex(DbSchema.FoodTable.Columns.RECIPE));
            String ingredients = getString(getColumnIndex(DbSchema.FoodTable.Columns.INGREDIENTS));

            Food food = new Food(calories, recipe, name, ingredients);
            return food;
        }
    }
}


