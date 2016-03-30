package com.wirelessorder.service;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wirelessorder.dao.UserDao;
import com.wirelessorder.po.User;

public class UserService {
	ApplicationContext context =   
            new ClassPathXmlApplicationContext("applicationContext.xml");  
	private UserDao userDao = (UserDao) context.getBean("userDao") ;
	
	public boolean isUserMatch(String userName, String userPassword) {
		int count = userDao.getMatchUserCount(userName, userPassword);
		return count > 0;
	}
	
	public User getUser(String userName, String userPassword) {
		return userDao.getUser(userName, userPassword);
	}
	
	public int addUser(User user) {
		return userDao.addNewUser(user);
	}
	
	public boolean isUserExists(String userName) {
		return userDao.isUserExists(userName);
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

}
