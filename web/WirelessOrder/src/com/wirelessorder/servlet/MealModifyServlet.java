package com.wirelessorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wirelessorder.po.Meal;
import com.wirelessorder.service.MealService;

public class MealModifyServlet extends HttpServlet {
	private MealService mealService = new MealService();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mealId = request.getParameter("meal_id");
		String mealName = request.getParameter("meal_name");
		String mealPrice = request.getParameter("meal_price");
		String mealInfo = request.getParameter("meal_info");
		
		Meal meal = mealService.getMealById(Integer.parseInt(mealId));
		meal.setMealName(mealName);
		meal.setMealPrice(Double.parseDouble(mealPrice));
		meal.setMealInfo(mealInfo);
		mealService.updateMeal(meal);
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		out.write("success");
		out.flush();
		out.close();
	}

}
