package com.wirelessorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.wirelessorder.mapper.OrderDetailMapper;
import com.wirelessorder.mapper.OrderMapper;
import com.wirelessorder.po.Order;
import com.wirelessorder.po.OrderDetail;

public class OrderDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}
	
	public int newOrder(final Order order) {
		final String sqlString = "INSERT INTO t_order (user_id, order_user_amount, order_sum, order_date) VALUES (?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, order.getUserId());
				ps.setInt(2, order.getUserAmount());
				ps.setDouble(3, order.getOrderSum());
				ps.setString(4, order.getOrderDate());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public List<Order> getOrderByUserId(int userId) {
		List<Order> orderList = null;
		String sqlString = "SELECT * FROM t_order WHERE user_id=?";
		orderList = jdbcTemplate.query(sqlString, new Object[]{userId}, new OrderMapper());
		return orderList;
	}
	
	public Order getOrderById(int orderId) {
		String sqlString = "SELECT * FROM t_order WHERE order_id=?";
		List<Order> orderList = null;
		orderList = jdbcTemplate.query(sqlString, new Object[]{orderId}, new OrderMapper());
		return orderList.get(0);
	}
	
	public int getOrderIdByUserAndTime(int userId, String time) {
		String sqlString = "SELECT order_id FROM t_order WHERE user_id = ? AND order_date = ?";
		List<Order> orderList = null;
		orderList = jdbcTemplate.query(sqlString, new Object[]{userId, time}, new OrderMapper());
		int orderId = orderList.get(0).getOrderId();
		return orderId;
	}
	
	public void addMealToOrder(int orderId, int mealId, int mealAmount) {
		String sqlString = "INSERT INTO t_order_detail (order_id, meal_id, meal_amount) VALUES (?, ?, ?)";
		jdbcTemplate.update(sqlString, new Object[]{orderId, mealId, mealAmount});
	}
	
	public void delMealFromOrder(int orderId, int mealId) {
		String sqlString = "DELETE FROM t_order_detail WHERE order_id=? AND meal_id=?";
		jdbcTemplate.update(sqlString, new Object[]{orderId, mealId});
	}
	
	public void updateOrderSum(final Order order) {
		String sqlString = "SELECT order_id, sum(meal_amount * meal_price) as the_order_sum" +
				" FROM t_order_detail INNER JOIN t_meal ON t_order_detail.meal_id = t_meal.meal_id" +
				" GROUP BY order_id HAVING order_id=?";
		jdbcTemplate.query(sqlString, new Object[]{order.getOrderId()},new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				order.setOrderSum(rs.getDouble("the_order_sum"));
			}
		});
		String sqlString2 = "UPDATE t_order SET order_sum = ?";
		jdbcTemplate.update(sqlString2, new Object[]{order.getOrderSum()});
	}
	
	public List<OrderDetail> getOrderDetailById(int orderId) {
		String sqlString = "SELECT * FROM t_order_detail WHERE order_id = ?";
		List<OrderDetail> deList = null;
		deList = jdbcTemplate.query(sqlString, new Object[]{orderId}, new OrderDetailMapper());
		return deList;
	}
	
	public List<Order> getAllOrders() {
		List<Order> orderList = null;
		String sqlString = "SELECT * FROM t_order";
		orderList = jdbcTemplate.query(sqlString, new OrderMapper());
		return orderList;
	}
	
	public List<OrderDetail> getAllOrderDetails() {
		String sqlString = "SELECT * FROM t_order_detail";
		List<OrderDetail> deList = null;
		deList = jdbcTemplate.query(sqlString, new OrderDetailMapper());
		return deList;
	}
	
}
