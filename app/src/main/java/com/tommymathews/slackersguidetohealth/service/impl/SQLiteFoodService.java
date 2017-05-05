package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.tommymathews.slackersguidetohealth.R;
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

        //prepopulate
        prepopulate(context);
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
    public List<Food> getFoodByCalorieRange(int calLow, int calHigh) {
        List<Food> foods = getAllFoods();
        List<Food> foodsInRange = new ArrayList<Food>();

        for (Food food: foods) {
            if (food.getCalorieLevel() >= calLow && food.getCalorieLevel() <= calHigh) {
                foodsInRange.add(food);
            }
        }

        return foodsInRange;
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

        Cursor cursor = database.query(DbSchema.FoodTable.FOOD_NAME, null,
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

        contentValues.put(DbSchema.FoodTable.Columns.ID, food.getId());
        contentValues.put(DbSchema.FoodTable.Columns.NAME, food.getName());
        contentValues.put(DbSchema.FoodTable.Columns.CALORIE_LEVEL, food.getCalorieLevel());
        contentValues.put(DbSchema.FoodTable.Columns.RECOMMENDATION, food.getRecommendation());
        contentValues.put(DbSchema.FoodTable.Columns.INGREDIENTS, food.getIngredients());
        contentValues.put(DbSchema.FoodTable.Columns.RECIPE, food.getRecipe());
        contentValues.put(DbSchema.FoodTable.Columns.IMAGE_PATH, food.getImagePath());

        return contentValues;
    }

    @Override
    public void addFood(Food food) {
        ContentValues contentValues = getContentValues(food);
        Food currFood = getFoodByName(food.getName());
        if (currFood == null) {
            database.insert(DbSchema.FoodTable.FOOD_NAME, null, contentValues);
        } else {
            database.update(DbSchema.FoodTable.FOOD_NAME, contentValues,
                    DbSchema.FoodTable.Columns.NAME + "=?", new String[]{currFood.getName()});
        }
    }

    private void prepopulate(Context context) {
        //eggplant
        Food food = new Food(120, context.getResources().getString(R.string.foodSuggestion),
                context.getResources().getString(R.string.process),
                context.getResources().getString(R.string.recipeIdea),
                context.getResources().getString(R.string.ingredients),
                "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.eggplant);
        //sloppy joes
        Food food1 = new Food(300, context.getResources().getString(R.string.foodSuggestion2),
                context.getResources().getString(R.string.process2),
                context.getResources().getString(R.string.recipeIdea2),
                context.getResources().getString(R.string.ingredients2),
                "android.resource://com.tommymathews.slackersguidetohealth/" +  R.drawable.sloppy_joe);
        //fish stew
        Food food2 = new Food(400, context.getResources().getString(R.string.foodSuggestion2),
                context.getResources().getString(R.string.process2),
                context.getResources().getString(R.string.recipeIdea2),
                context.getResources().getString(R.string.ingredients2),
                "android.resource://com.tommymathews.slackersguidetohealth/" +  R.drawable.fish_stew);
        //strawberry crush
        Food food3 = new Food(640, context.getResources().getString(R.string.foodSuggestion3),
                context.getResources().getString(R.string.process3),
                context.getResources().getString(R.string.recipeIdea3),
                context.getResources().getString(R.string.ingredients3),
                "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.strawberry_crush);
        //chocolate peanut butter shake
        Food food4 = new Food(1070, context.getResources().getString(R.string.foodSuggestion4),
                context.getResources().getString(R.string.process4),
                context.getResources().getString(R.string.recipeIdea4),
                context.getResources().getString(R.string.ingredients4),
                "android.resource://com.tommymathews.slackersguidetohealth/" +  R.drawable.chocolate_pb_shake);
        //donut muffins
        Food food5 = new Food(50, context.getResources().getString(R.string.foodSuggestion5),
                context.getResources().getString(R.string.process5),
                context.getResources().getString(R.string.recipeIdea5),
                context.getResources().getString(R.string.ingredients5),
                "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.donut_muffin);
        //baked omelet squares
        Food food6 = new Food(570, context.getResources().getString(R.string.foodSuggestion6),
                context.getResources().getString(R.string.process6),
                context.getResources().getString(R.string.recipeIdea6),
                context.getResources().getString(R.string.ingredients6),
                "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.baked_omelet_squares);
        addFood(food);
        addFood(food1);
        addFood(food2);
        addFood(food3);
        addFood(food4);
        addFood(food5);
        addFood(food6);
    }

    private class FoodCursorWrapper extends CursorWrapper {
        public FoodCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Food getFood() {
            String id = getString(getColumnIndex(DbSchema.FoodTable.Columns.ID));
            String name = getString(getColumnIndex(DbSchema.FoodTable.Columns.NAME));
            int calories = getInt(getColumnIndex(DbSchema.FoodTable.Columns.CALORIE_LEVEL));
            String recommendation = getString(getColumnIndex(DbSchema.FoodTable.Columns.RECOMMENDATION));
            String recipe = getString(getColumnIndex(DbSchema.FoodTable.Columns.RECIPE));
            String ingredients = getString(getColumnIndex(DbSchema.FoodTable.Columns.INGREDIENTS));
            String imagePath = getString(getColumnIndex(DbSchema.FoodTable.Columns.IMAGE_PATH));

            Food food = new Food(id, calories, recommendation, recipe, name, ingredients, imagePath);
            return food;
        }
    }
}


