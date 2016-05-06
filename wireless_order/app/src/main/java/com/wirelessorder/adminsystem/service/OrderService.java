package com.wirelessorder.adminsystem.service;

import android.content.Context;
import android.database.Cursor;

import com.wirelessorder.adminsystem.dao.OrderDao;
import com.wirelessorder.adminsystem.po.Order;
import com.wirelessorder.adminsystem.po.OrderDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by triplez on 16-3-30.
 */
public class OrderService {
    private OrderDao orderDao;
    public OrderService(Context context) {
        orderDao = new OrderDao(context);
    }

    public void insertOrders (JSONArray orderArray) {
        orderDao.clearOrder();
        for (int i = 0; i < orderArray.length(); i++) {
            try {
                JSONObject orderJson = orderArray.getJSONObject(i);
                Order order = new Order();
                order.setOrderId(orderJson.getInt("order_id"));
                order.setUserId(orderJson.getInt("user_id"));
                order.setUserAmount(orderJson.getInt("order_user_amount"));
                order.setOrderSum(orderJson.getDouble("order_sum"));
                order.setOrderDate(orderJson.getString("order_date"));
                orderDao.insertOrder(order);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertOrderDetails (JSONArray orderDetailArray) {
        orderDao.clearOrderDetail();
        for (int i = 0; i < orderDetailArray.length(); i++) {
            try {
                JSONObject orderDetailJson = orderDetailArray.getJSONObject(i);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderDetailJson.getInt("order_id"));
                orderDetail.setMealId(orderDetailJson.getInt("meal_id"));
                orderDetail.setMealAmount(orderDetailJson.getInt("meal_amount"));
                orderDao.insertOrderDetail(orderDetail);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public Order getOrderByUserId(int userId) {
        Order order = new Order();
        Cursor c = orderDao.getOrderByUserId(String.valueOf(userId));
        while(c.moveToNext()) {
            order.setUserId(userId);
            order.setOrderId(c.getInt(c.getColumnIndex("order_id")));
            order.setUserAmount(c.getInt(c.getColumnIndex("order_user_amount")));
            order.setOrderDate(c.getString(c.getColumnIndex("order_sum")));
            order.setOrderDate(c.getString(c.getColumnIndex("order_date")));
        }
        return order;
    }

    public List<OrderDetail> getOrderDetailByOrderId(int orderId) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Cursor c = orderDao.getOrderDetailByOrderId(String.valueOf(orderId));
        while(c.moveToNext()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setMealId(c.getInt(c.getColumnIndex("meal_id")));
            orderDetail.setMealAmount(c.getInt(c.getColumnIndex("meal_amount")));
            orderDetailList.add(orderDetail);
        }
        return  orderDetailList;
    }

    public int getOrderAmount() {
        return orderDao.getAllOrders().size();
    }

    public double getAllOrderSum() {
        List<Order> orderList = orderDao.getAllOrders();
        Double sum = 0.0;
        for (Order order : orderList) {
            sum += order.getOrderSum();
        }
        return sum;
    }

    public void closeDB() {
        orderDao.closeDB();
    }
}
