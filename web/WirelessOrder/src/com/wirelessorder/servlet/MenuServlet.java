package com.wirelessorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wirelessorder.po.Meal;
import com.wirelessorder.service.MealService;

public class MenuServlet extends HttpServlet {
	private MealService mealService = new MealService();
	final int TYPE_ALL = 0;
	final int TYPE_COLD = 1;
	final int TYPE_HOT = 2;
	final int TYPE_MAIN = 3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		int mealType = Integer.parseInt(type);
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		JSONArray resultArray = new JSONArray();

		try {
			if (mealType == TYPE_ALL) {
				List<Meal> mealList = mealService.getAllMeal();
				for (Meal meal : mealList) {
					JSONObject mealItem = new JSONObject();
					mealItem.put("meal_id", meal.getMealId());
					mealItem.put("meal_type", meal.getMealType());
					mealItem.put("meal_name", meal.getMealName());
					mealItem.put("meal_price", meal.getMealPrice());
					mealItem.put("meal_image_url", meal.getMealImage());
					mealItem.put("meal_info", meal.getMealInfo());
					resultArray.put(mealItem);
				}
			} else {
				List<Meal> mealList = mealService.getAllMealByType(mealType);
				for (Meal meal : mealList) {
					JSONObject mealItem = new JSONObject();
					mealItem.put("meal_id", meal.getMealId());
					mealItem.put("meal_type", meal.getMealType());
					mealItem.put("meal_name", meal.getMealName());
					mealItem.put("meal_price", meal.getMealPrice());
					mealItem.put("meal_image_url", meal.getMealImage());
					mealItem.put("meal_info", meal.getMealInfo());
					resultArray.put(mealItem);
				}
			}
			out.write(resultArray.toString());
			out.flush();
			out.close();
		} catch (JSONException e) {
			// TODO: handle exception
		}
	}

}
