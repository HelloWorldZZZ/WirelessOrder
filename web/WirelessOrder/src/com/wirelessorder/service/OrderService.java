package com.wirelessorder.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wirelessorder.dao.OrderDao;
import com.wirelessorder.po.Order;
import com.wirelessorder.po.OrderDetail;

public class OrderService {
	ApplicationContext context =   
            new ClassPathXmlApplicationContext("applicationContext.xml");
	private OrderDao orderDao = (OrderDao) context.getBean("orderDao");
	
	public int addOrder(Order order) {
		return orderDao.newOrder(order);
	}
	
	public List<Order> getAllOrderByUserId(int userId) {
		return orderDao.getOrderByUserId(userId);
	}
	
	public void addMealToOrder(int orderId, int mealId, int mealAmount) {
		orderDao.addMealToOrder(orderId, mealId, mealAmount);
	}
	
	public void delMealFromOrder(int orderId, int mealId) {
		orderDao.delMealFromOrder(orderId, mealId);
	}
	
	public Order getOrderById(int orderId) {
		return orderDao.getOrderById(orderId);
	}
	
	public void updateOrderSum(Order order) {
		orderDao.updateOrderSum(order);
	}
	
	public List<OrderDetail> getOrderDetailById(int orderId) {
		return orderDao.getOrderDetailById(orderId);
	}
	
	public List<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}
	
	public List<OrderDetail> getAllOrderDetails() {
		return orderDao.getAllOrderDetails();
	}
 
}
