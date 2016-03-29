package com.wirelessorder.dao;

import javax.sql.DataSource;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.wirelessorder.mapper.MealMapper;
import com.wirelessorder.po.Meal;

public class MealDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}
	
	public List<Meal> getAllMealByType(int mealType) {
		String sqlString = "SELECT * FROM t_meal WHERE meal_type_id=?";
		List<Meal> mealList = null;
		mealList = jdbcTemplate.query(sqlString, new Object[]{mealType}, new MealMapper());
		return mealList;
	}
	
	public List<Meal> getAllMeal() {
		String sqlString = "SELECT * FROM t_meal";
		List<Meal> mealList = null;
		mealList = jdbcTemplate.query(sqlString, new MealMapper());
		return mealList;
	}
	
	public void addMeal(Meal meal) {
		String sqlString = "INSERT INTO t_meal (meal_type_id, meal_name, meal_price, meal_image, meal_info) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sqlString,new Object[] {meal.getMealType(), meal.getMealName(),
				meal.getMealPrice(), meal.getMealImage(), meal.getMealInfo()});
	}
	
	public void updateMeal(Meal meal) {
		String sqlString = "UPDATE t_meal SET meal_type_id=?, meal_name=?, meal_price=?," +
				"meal_image=?, meal_info=? WHERE meal_id=?";
		jdbcTemplate.update(sqlString, new Object[]{meal.getMealType(), meal.getMealName(),
				meal.getMealPrice(), meal.getMealImage(), meal.getMealInfo(), meal.getMealId()});
	}
	
	public Meal getMealById(int mealId) {
		String sqlString = "SELECT * FROM t_meal WHERE meal_id=?";
		List<Meal> mealList = null;
		mealList = jdbcTemplate.query(sqlString, new Object[]{mealId}, new MealMapper());
		return mealList.get(0);
	}

}
