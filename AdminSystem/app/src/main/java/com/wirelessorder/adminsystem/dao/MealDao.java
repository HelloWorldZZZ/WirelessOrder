package com.wirelessorder.adminsystem.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wirelessorder.adminsystem.po.Meal;
import com.wirelessorder.adminsystem.utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by triplez on 16-3-30.
 */
public class MealDao {
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mDB;
    final private String TABLE_NAME = "t_meal";
    public MealDao(Context context) {
        mDataBaseHelper = new DataBaseHelper(context);
        mDB = mDataBaseHelper.getWritableDatabase();
    }

    public void clearMeal() {
        String sql = "DELETE FROM " + TABLE_NAME;
        mDB.execSQL(sql);
    }

    public void insertMeal(Meal meal) {
        ContentValues cv = new ContentValues();
        cv.put("meal_id", meal.getMealId());
        cv.put("meal_type_id", meal.getMealType());
        cv.put("meal_name", meal.getMealName());
        cv.put("meal_price", meal.getMealPrice());
        cv.put("meal_image", meal.getMealImage());
        cv.put("meal_info", meal.getMealInfo());
        mDB.insert(TABLE_NAME, null, cv);
    }

    public List<Meal> getAllMeals() {
        List<Meal> mealList = new ArrayList<>();
        String sql = "SELECT * FROM t_meal";
        Cursor c = mDB.rawQuery(sql, null);
        while (c.moveToNext()) {
            int mealId = c.getInt(c.getColumnIndex("meal_id"));
            int mealType = c.getInt(c.getColumnIndex("meal_type_id"));
            String mealName = c.getString(c.getColumnIndex("meal_name"));
            double mealPrice = c.getDouble(c.getColumnIndex("meal_price"));
            String mealImage = c.getString(c.getColumnIndex("meal_image"));
            String mealInfo = c.getString(c.getColumnIndex("meal_info"));
            Meal meal = new Meal(mealType, mealName, mealPrice, mealImage, mealInfo);
            meal.setMealId(mealId);
            mealList.add(meal);
        }
        c.close();
        return mealList;
    }

    public void closeDB() {
        mDB.close();
    }
}
