package com.wirelessorder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wirelessorder.po.Order;

public class OrderMapper implements RowMapper<Order>{
	
	public Order mapRow(ResultSet rs, int rownum) throws SQLException {  
		Order order = new Order();
		order.setOrderId(rs.getInt("order_id"));
		order.setUserId(rs.getInt("user_id"));
		order.setUserAmount(rs.getInt("order_user_amount"));
		order.setOrderSum(rs.getDouble("order_sum"));
		order.setOrderDate(rs.getString("order_date"));
        return order;       
    } 
	
}
