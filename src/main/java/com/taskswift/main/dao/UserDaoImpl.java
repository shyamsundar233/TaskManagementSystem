//$Id$
package com.taskswift.main.dao;

import com.taskswift.main.util.TenantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taskswift.main.entity.User;
import com.taskswift.main.repo.UserRepo;

import java.util.List;
import java.util.Optional;

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

	@Override
	public User getUserById(Long userId) {
		return userRepo.findUserByUseridWithinRange(userId, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAllByUseridBetween(TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

}
