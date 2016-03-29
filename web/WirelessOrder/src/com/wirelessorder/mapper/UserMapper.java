package com.wirelessorder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wirelessorder.po.User;

public class UserMapper implements RowMapper<User>{
	
	public User mapRow(ResultSet rs, int rownum) throws SQLException {  
		User user = new User();
		user.setUserId(rs.getInt("user_id"));
		user.setUserName(rs.getString("user_name"));
		user.setUserPassword(rs.getString("user_password"));
		user.setUserPhone(rs.getString("user_phone"));
        return user;       
    } 

}
