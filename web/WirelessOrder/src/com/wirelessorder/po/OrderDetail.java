package com.wirelessorder.po;

public class OrderDetail {
	private int orderId;
	private int mealId;
	private int mealAmount;
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getMealId() {
		return mealId;
	}
	public void setMealId(int mealId) {
		this.mealId = mealId;
	}
	public int getMealAmount() {
		return mealAmount;
	}
	public void setMealAmount(int mealAmount) {
		this.mealAmount = mealAmount;
	}

}
