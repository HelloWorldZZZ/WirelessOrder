package com.wirelessorder.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.wirelessorder.mapper.UserMapper;
import com.wirelessorder.po.User;
import com.wirelessorder.utils.Encipher;

public class UserDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public int getMatchUserCount(String userName, String userPassword) {
		String sqlStr = "SELECT count(*) FROM t_user WHERE user_name=? AND user_password=?";
		return jdbcTemplate.queryForInt(sqlStr, new Object[] {userName, Encipher.encrypt(userPassword)});
	}
	
	public boolean isUserExists(String userName) {
		String sqlString = "SELECT count(*) FROM t_user WHERE user_name=?";
		int count = jdbcTemplate.queryForInt(sqlString, new Object[]{userName});
		return count > 0;
	}
	
	public int addNewUser(final User user) {
		final String sqlString = "INSERT INTO t_user (user_name, user_password, user_phone) VALUES (?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getUserName());
				ps.setString(2, Encipher.encrypt(user.getUserPassword()));
				ps.setString(3, user.getUserPhone());
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public User getUser(String userName, String userPassword) {
		User user;
		String sqlStr = "SELECT * FROM t_user WHERE user_name=? AND user_password=?";
		user = jdbcTemplate.query(sqlStr, new Object[] {userName, Encipher.encrypt(userPassword)}, new UserMapper()).get(0);
		return user;
	}
	
	public List<User> getAllUsers() {
		List<User> users = null;
		String sqlString = "SELECT * FROM t_user";
		users = jdbcTemplate.query(sqlString, new UserMapper());
		return users;
	}

}
