package com.wirelessorder.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wirelessorder.po.Admin;
import com.wirelessorder.service.AdminService;

public class AdminLoginServlet extends HttpServlet {
	private AdminService adminService = new AdminService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String adminName = request.getParameter("adminName");
		String adminPassword = request.getParameter("adminPassword");
		
		boolean isAdminValid = adminService.isAdminMatch(adminName, adminPassword);
		if (isAdminValid) {
			Admin admin = adminService.getAdmin(adminName, adminPassword);
			response.setContentType("text/html;charset=UTF-8;");
			PrintWriter out = response.getWriter();
			out.println(admin.getAdminId());
			out.println(admin.getAdminName());
			out.println(admin.getAdminPassword());
			out.flush();
			out.close();
		} else {
			response.setContentType("text/html;charset=UTF-8;");
			PrintWriter out = response.getWriter();
			out.println("error");
			out.flush();
			out.close();
		}
	}

}
