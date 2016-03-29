package com.wirelessorder.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wirelessorder.po.Admin;

public class AdminMapper implements RowMapper<Admin>{
	
	public Admin mapRow(ResultSet rs, int rownum) throws SQLException {  
		Admin admin = new Admin();
		admin.setAdminId(rs.getInt("admin_id"));
		admin.setAdminName(rs.getString("admin_name"));
		admin.setAdminPassword(rs.getString("admin_password"));
        return admin;       
    } 

}
