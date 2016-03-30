package com.wirelessorder.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wirelessorder.dao.AdminDao;
import com.wirelessorder.po.Admin;

public class AdminService {
	ApplicationContext context =   
            new ClassPathXmlApplicationContext("applicationContext.xml");  
	private AdminDao adminDao = (AdminDao) context.getBean("adminDao") ;
	
	public boolean isAdminMatch(String adminName, String adminPassword) {
		int count = adminDao.getMatchAdminCount(adminName, adminPassword);
		return count > 0;
	}
	
	public Admin getAdmin(String adminName, String adminPassword) {
		return adminDao.getAdmin(adminName, adminPassword);
	}
	
	public void addAdmin(Admin admin) {
		adminDao.addNewAdmin(admin);
	}
	
	public List<Admin> getAllAdmins() {
		return adminDao.getAllAdmins();
	}

}
