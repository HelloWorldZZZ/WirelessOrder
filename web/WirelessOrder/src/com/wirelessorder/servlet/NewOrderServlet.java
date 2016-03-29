package com.wirelessorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wirelessorder.po.Order;
import com.wirelessorder.service.OrderService;
import com.wirelessorder.utils.DateGen;

public class NewOrderServlet extends HttpServlet {
	private OrderService orderService = new OrderService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderString = request.getParameter("order");
		JSONObject jsonOrder;
		try {
			jsonOrder = new JSONObject(orderString);
			String timeString = DateGen.getTimeStr();
			int userId = jsonOrder.getInt("userId");
			Order order = new Order(userId, jsonOrder.getInt("userAmount"), 
					jsonOrder.getDouble("orderSum"), timeString);
			int orderId = orderService.addOrder(order);
			
			JSONArray mealList = jsonOrder.getJSONArray("mealList");
			for (int i = 0; i < mealList.length(); i++) {
				JSONObject jsonMeal = mealList.getJSONObject(i);
				int mealId = jsonMeal.getInt("mealId");
				int mealAmount = jsonMeal.getInt("mealAmount");
				orderService.addMealToOrder(orderId, mealId, mealAmount);
			}
			orderService.updateOrderSum(orderService.getOrderById(orderId));
			
			response.setContentType("text/html;charset=UTF-8;");
			PrintWriter out = response.getWriter();
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("order_id", orderId);
			jsonResponse.put("user_id", userId);
			jsonResponse.put("user_amount", jsonOrder.getInt("userAmount"));
			jsonResponse.put("order_date", timeString);
			out.write(jsonResponse.toString());
			out.flush();
			out.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
