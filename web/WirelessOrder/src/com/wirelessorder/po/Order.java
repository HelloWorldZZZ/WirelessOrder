package com.wirelessorder.po;

public class Order {
	private int orderId;
	private int userId;
	private int userAmount;
	private double orderSum;
	private String orderDate;
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Order(int userId, int userAmount, double orderSum, String orderDate) {
		super();
		this.userId = userId;
		this.userAmount = userAmount;
		this.orderSum = orderSum;
		this.orderDate = orderDate;
	}
	public int getOrderId() {
		return orderId;
	}
	public Order() {
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getUserAmount() {
		return userAmount;
	}
	public void setUserAmount(int userAmount) {
		this.userAmount = userAmount;
	}
	public double getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(double orderSum) {
		this.orderSum = orderSum;
	}

}
