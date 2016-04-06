package com.wirelessorder.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import com.wirelessorder.po.Meal;
import com.wirelessorder.service.MealService;
import com.wirelessorder.utils.DateGen;

public class MealAddServlet extends HttpServlet {
	private MealService mealService = new MealService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Meal meal = new Meal();
		DiskFileItemFactory dfif = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(dfif);
        sfu.setHeaderEncoding("utf-8");
        
        try {
        	List<FileItem> items = sfu.parseRequest(request);
            for (int i = 0; i < items.size(); i++) {
                FileItem item = items.get(i);
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = new String(item.getString("utf-8"));
                    if (name.equalsIgnoreCase("mealType")) {
                    	meal.setMealType(Integer.parseInt(value));
                    } else if (name.equalsIgnoreCase("mealName")) {
                    	meal.setMealName(value);
                    } else if (name.equalsIgnoreCase("mealPrice")) {
                    	meal.setMealPrice(Double.parseDouble(value));
                    } else if (name.equalsIgnoreCase("mealInfo")) {
                    	meal.setMealInfo(value);
                    }
                } else {
                    ServletContext sc = this.getServletContext();
                    String path = sc.getRealPath("menu");
                    String fileName = item.getName();
                    String imagePath = "http://www.zhouzezhou.site/WirelessOrder/menu/" + fileName;
                    meal.setMealImage(imagePath);
                    File file = new File(path + "//" + fileName);
                    item.write(file);
                }
            }
        } catch (Exception e) {
			// TODO: handle exception
		}

		response.setContentType("text/html;charset=UTF-8;");
		PrintWriter out = response.getWriter();
		
		int mealId = mealService.addMeal(meal);
		JSONObject mealJson = new JSONObject();
		try {
			mealJson.put("mealId", mealId);
			mealJson.put("mealType", meal.getMealType());
			mealJson.put("mealName", meal.getMealName());
			mealJson.put("mealPrice", meal.getMealPrice());
			mealJson.put("mealImage", meal.getMealImage());
			mealJson.put("mealInfo", meal.getMealInfo());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write(mealJson.toString());
		out.flush();
		out.close();
	}

}
