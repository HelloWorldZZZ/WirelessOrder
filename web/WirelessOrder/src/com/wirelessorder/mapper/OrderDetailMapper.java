package com.wirelessorder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wirelessorder.po.OrderDetail;

public class OrderDetailMapper implements RowMapper<OrderDetail>{
	
	public OrderDetail mapRow(ResultSet rs, int rownum) throws SQLException {  
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(rs.getInt("order_id"));
		orderDetail.setMealId(rs.getInt("meal_id"));
		orderDetail.setMealAmount(rs.getInt("meal_amount"));
        return orderDetail;       
    } 
	
}
