package com.wirelessorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.wirelessorder.po.User;
import com.wirelessorder.service.UserService;

public class UserRegServlet extends HttpServlet {
	private UserService userService = new UserService();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("password");
		String userPhone = request.getParameter("phone");
		User user = new User(userName, userPassword, userPhone);
		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		JSONObject result = new JSONObject();

		try {
			if (userService.isUserExists(userName)) {
				result.put("regSuccess", 0);
			} else {
				int userId = userService.addUser(user);
				result.put("regSuccess", 1);
				result.put("user_id", userId);
			}
			out.write(result.toString());
			out.flush();
			out.close();
		} catch (JSONException e) {
		}
	}

}
