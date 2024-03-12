//$Id$
package com.taskswift.main.service;

import com.taskswift.main.entity.User;

import java.util.List;

public interface UserService {
	
	public String saveUser(User user);

	public User getUserByName(String name);

	public User getUserById(Long userId);

	public List<User> getAllUsers();

}
