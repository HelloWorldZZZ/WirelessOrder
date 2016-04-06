package com.wirelessorder.adminsystem.po;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Meal {
	private int mealId;
	private int mealType;
	private String mealName;
	private double mealPrice;
	private ImageView mealView;
	public Meal(int mealType, String mealName, double mealPrice,
			String mealImage, String mealInfo) {
		super();
		this.mealType = mealType;
		this.mealName = mealName;
		this.mealPrice = mealPrice;
		this.mealImage = mealImage;
		this.mealInfo = mealInfo;
	}
	public Meal() {
	}
	private String mealImage;
	
	public int getMealId() {
		return mealId;
	}
	public void setMealId(int mealId) {
		this.mealId = mealId;
	}
	public int getMealType() {
		return mealType;
	}
	public void setMealType(int mealType) {
		this.mealType = mealType;
	}
	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public double getMealPrice() {
		return mealPrice;
	}
	public void setMealPrice(double mealPrice) {
		this.mealPrice = mealPrice;
	}
	public String getMealImage() {
		return mealImage;
	}
	public void setMealImage(String mealImage) {
		this.mealImage = mealImage;
	}
	public String getMealInfo() {
		return mealInfo;
	}
	public void setMealInfo(String mealInfo) {
		this.mealInfo = mealInfo;
	}
	private String mealInfo;

	public ImageView getMealView() {
		return mealView;
	}

	public void setMealView(ImageView mealView) {
		this.mealView = mealView;
	}
}
