package com.wirelessorder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wirelessorder.po.Meal;

public class MealMapper implements RowMapper<Meal>{
	
	public Meal mapRow(ResultSet rs, int rownum) throws SQLException {
		Meal meal = new Meal();
		meal.setMealId(rs.getInt("meal_id"));
		meal.setMealType(rs.getInt("meal_type_id"));
		meal.setMealName(rs.getString("meal_name"));
		meal.setMealPrice(rs.getDouble("meal_price"));
		meal.setMealImage(rs.getString("meal_image"));
		meal.setMealInfo(rs.getString("meal_info"));
		
		return meal;
	}

}
