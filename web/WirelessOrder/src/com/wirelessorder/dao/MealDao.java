package com.wirelessorder.dao;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
	
	public int addMeal(final Meal meal) {
		final String sqlString = "INSERT INTO t_meal (meal_type_id, meal_name, meal_price, meal_image, meal_info) VALUES (?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, meal.getMealType());
				ps.setString(2, meal.getMealName());
				ps.setDouble(3, meal.getMealPrice());
				ps.setString(4, meal.getMealImage());
				ps.setString(5, meal.getMealInfo());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
		/*jdbcTemplate.update(sqlString,new Object[] {meal.getMealType(), meal.getMealName(),
				meal.getMealPrice(), meal.getMealImage(), meal.getMealInfo()});*/
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
