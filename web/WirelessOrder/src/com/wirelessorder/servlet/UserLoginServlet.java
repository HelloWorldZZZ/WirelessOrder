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

public class UserLoginServlet extends HttpServlet {
	private UserService userService = new UserService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("userName");//获取用户名
		String password = request.getParameter("password");//获取密码
		
		boolean isUserValid = userService.isUserMatch(userName, password);//数据库中匹配用户信息
		if (isUserValid) {
			/*登录成功*/
			User user = userService.getUser(userName, password);//获取用户完整信息
			response.setContentType("text/html;charset=UTF-8;");
			PrintWriter out = response.getWriter();
			JSONObject result = new JSONObject();
			try {
				result.put("loginSuccess", 1);
				result.put("user_id", user.getUserId());
				out.write(result.toString());
				out.flush();
				out.close();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			/*登录失败*/
			response.setContentType("text/html;charset=UTF-8;");
			PrintWriter out = response.getWriter();
			JSONObject result = new JSONObject();
			try {
				result.put("loginSuccess", 0);
				out.write(result.toString());
				out.flush();
				out.close();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
