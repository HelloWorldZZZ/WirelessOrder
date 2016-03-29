package com.wirelessorder.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.wirelessorder.mapper.AdminMapper;
import com.wirelessorder.po.Admin;
import com.wirelessorder.utils.Encipher;

public class AdminDao {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}

	public int getMatchAdminCount(String adminName, String adminPassword) {
		String sqlStr = "SELECT count(*) FROM t_admin WHERE admin_name=? AND admin_password=?";
		return jdbcTemplate.queryForInt(sqlStr, new Object[] {adminName, Encipher.encrypt(adminPassword)});
	}
	
	public void addNewAdmin(Admin admin) {
		String sqlStr = "INSERT INTO t_admin (admin_name, admin_password) VALUES (?, ?)";
		jdbcTemplate.update(sqlStr, new Object[] { admin.getAdminName(), Encipher.encrypt(admin.getAdminPassword())});
	}
	
	public Admin getAdmin(String adminName, String adminPassword) {
		Admin admin;
		String sqlStr = "SELECT * FROM t_admin WHERE admin_name=? AND admin_password=?";
		admin = jdbcTemplate.query(sqlStr, new Object[] {adminName, Encipher.encrypt(adminPassword)}, new AdminMapper()).get(0);
		return admin;
	}

}
