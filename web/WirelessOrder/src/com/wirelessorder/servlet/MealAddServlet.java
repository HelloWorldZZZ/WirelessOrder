package com.wirelessorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wirelessorder.po.Meal;
import com.wirelessorder.service.MealService;

public class MealAddServlet extends HttpServlet {
	private MealService mealService = new MealService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String mealTypeString = request.getParameter("mealType");
		String mealName = request.getParameter("mealName");
		String mealPriceString = request.getParameter("mealPrice");
		String mealImage = request.getParameter("mealImage");
		String mealInfo = request.getParameter("mealInfo");
		
		int mealType = Integer.parseInt(mealTypeString);
		double mealPrice = Double.parseDouble(mealPriceString);
		
		Meal meal = new Meal(mealType, mealName, mealPrice, mealImage, mealInfo);

		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		out.println(mealName + "---" + mealInfo);
		
		mealService.addMeal(meal);
		out.print("添加成功");
		out.flush();
		out.close();
	}

}
