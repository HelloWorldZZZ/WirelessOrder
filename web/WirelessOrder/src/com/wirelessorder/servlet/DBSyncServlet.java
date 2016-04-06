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

import com.wirelessorder.po.Admin;
import com.wirelessorder.po.Meal;
import com.wirelessorder.po.Order;
import com.wirelessorder.po.OrderDetail;
import com.wirelessorder.po.User;
import com.wirelessorder.service.AdminService;
import com.wirelessorder.service.MealService;
import com.wirelessorder.service.OrderService;
import com.wirelessorder.service.UserService;

public class DBSyncServlet extends HttpServlet {
	private MealService mealService = new MealService();
	private OrderService orderService = new OrderService();
	private UserService userService = new UserService();
	private AdminService adminService = new AdminService();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8;");
		List<User> userList = userService.getAllUsers();
		List<Admin> adminList = adminService.getAllAdmins();
		List<Meal> mealList = mealService.getAllMeal();
		List<Order> orderList = orderService.getAllOrders();
		List<OrderDetail> orderDetailList = orderService.getAllOrderDetails();
		JSONArray userArray = new JSONArray();
		JSONArray adminArray = new JSONArray();
		JSONArray mealArray = new JSONArray();
		JSONArray orderArray = new JSONArray();
		JSONArray orderDetailArray = new JSONArray();
		PrintWriter out = response.getWriter();

		try {
			for(User user : userList) {
				JSONObject userJson = new JSONObject();
				userJson.put("user_id", user.getUserId());
				userJson.put("user_password", user.getUserPassword());
				userJson.put("user_name", user.getUserName());
				userJson.put("user_phone", user.getUserPhone());
				userArray.put(userJson);
			}
			for(Admin admin : adminList) {
				JSONObject adminJson = new JSONObject();
				adminJson.put("admin_id", admin.getAdminId());
				adminJson.put("admin_password", admin.getAdminPassword());
				adminJson.put("admin_name", admin.getAdminName());
				adminArray.put(adminJson);
			}
			for(Meal meal : mealList) {
				JSONObject mealJson = new JSONObject();
				mealJson.put("meal_id", meal.getMealId());
				mealJson.put("meal_type_id", meal.getMealType());
				mealJson.put("meal_name", meal.getMealName());
				mealJson.put("meal_price", meal.getMealPrice());
				mealJson.put("meal_image", meal.getMealImage());
				mealJson.put("meal_info", meal.getMealInfo());
				mealArray.put(mealJson);
			}
			for(Order order : orderList) {
				JSONObject orderJson = new JSONObject();
				orderJson.put("order_id", order.getOrderId());
				orderJson.put("user_id", order.getUserId());
				orderJson.put("order_user_amount", order.getUserAmount());
				orderJson.put("order_sum", order.getOrderSum());
				orderJson.put("order_date", order.getOrderDate());
				orderArray.put(orderJson);
			}
			for(OrderDetail orderDetail : orderDetailList) {
				JSONObject orderDetailJson = new JSONObject();
				orderDetailJson.put("order_id", orderDetail.getOrderId());
				orderDetailJson.put("meal_id", orderDetail.getMealId());
				orderDetailJson.put("meal_amount", orderDetail.getMealAmount());
				orderDetailArray.put(orderDetailJson);
			}
			JSONObject dbJsonObject = new JSONObject();
			dbJsonObject.put("t_user", userArray);
			dbJsonObject.put("t_admin", adminArray);
			dbJsonObject.put("t_meal", mealArray);
			dbJsonObject.put("t_order", orderArray);
			dbJsonObject.put("t_order_detail", orderDetailArray);
			out.write(dbJsonObject.toString());
			out.flush();
			out.close();
		} catch (JSONException e) {
			// TODO: handle exception
		}
	}

}
