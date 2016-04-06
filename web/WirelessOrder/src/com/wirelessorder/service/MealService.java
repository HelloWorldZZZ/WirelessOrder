package com.wirelessorder.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wirelessorder.dao.MealDao;
import com.wirelessorder.po.Meal;

public class MealService {
	ApplicationContext context =   
            new ClassPathXmlApplicationContext("applicationContext.xml");
	private MealDao mealDao = (MealDao) context.getBean("mealDao");
	
	public List<Meal> getAllMeal() {
		return mealDao.getAllMeal();
	}
	
	public List<Meal> getAllMealByType(int mealType) {
		return mealDao.getAllMealByType(mealType);
	}
	
	public int addMeal(Meal meal) {
		return mealDao.addMeal(meal);
	}
	
	public Meal getMealById(int mealId) {
		return mealDao.getMealById(mealId);
	}
	
	public void updateMeal(Meal meal) {
		mealDao.updateMeal(meal);
	}

}
