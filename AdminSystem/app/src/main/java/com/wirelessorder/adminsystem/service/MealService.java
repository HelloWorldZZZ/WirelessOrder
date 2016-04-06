package com.wirelessorder.adminsystem.service;

import android.content.Context;
import android.database.Cursor;

import com.wirelessorder.adminsystem.dao.MealDao;
import com.wirelessorder.adminsystem.po.Meal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by triplez on 16-3-30.
 */
public class MealService {
    private MealDao mealDao;
    public MealService(Context context) {
        mealDao = new MealDao(context);
    }

    public void insertMeals (JSONArray mealArray) {
        mealDao.clearMeal();
        for (int i = 0; i < mealArray.length(); i++) {
            try {
                JSONObject mealJson = mealArray.getJSONObject(i);
                Meal meal = new Meal();
                meal.setMealId(mealJson.getInt("meal_id"));
                meal.setMealType(mealJson.getInt("meal_type_id"));
                meal.setMealName(mealJson.getString("meal_name"));
                meal.setMealPrice(mealJson.getInt("meal_price"));
                meal.setMealImage(mealJson.getString("meal_image"));
                meal.setMealInfo(mealJson.getString("meal_info"));
                mealDao.insertMeal(meal);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertMeal(Meal meal) {
        mealDao.insertMeal(meal);
    }

    public int getMealAmount() {
        return mealDao.getAllMeals().size();
    }

    public ArrayList getAllMeals() {
        return mealDao.getAllMeals();
    }

    public void updateMeal(Meal meal) {
        mealDao.updateMeal(meal);
    }

    public void deleteMeal(int mealId) {
        mealDao.deleteMeal(mealId);
    }

    public void closeDB() {
        mealDao.closeDB();
    }
}
