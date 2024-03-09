//$Id$
package com.taskswift.main.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taskswift.main.entity.User;
import com.taskswift.main.repo.UserRepo;

@Repository
@SuppressWarnings("null")
public class UserDaoImpl implements UserDao {
	
	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private UserRepo userRepo;

	@Override
	public User getUserByName(String name) {
		return userRepo.getUserByName(name);
	}

	@Override
	public void saveUser(User user) {		
		userRepo.save(user);		
		logger.info(">>> User saved to DB");
	}

}
