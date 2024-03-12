//$Id$
package com.taskswift.main.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskswift.main.dao.UserDao;
import com.taskswift.main.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;
	
	@Override
	public String saveUser(User user) {
		logger.info(">>> Saving User to DB");
		userDao.saveUser(user);
		return "SUCCESS";
	}

	@Override
	public User getUserByName(String name) {
		return userDao.getUserByName(name);
	}

	@Override
	public User getUserById(Long userId) {
		return userDao.getUserById(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

}
