//$Id$
package com.taskswift.main.dao;

import com.taskswift.main.entity.User;

import java.util.List;

public interface UserDao {

	public User getUserByName(String name);

	public void saveUser(User user);

	public User getUserById(Long userId);

	public List<User> getAllUsers();

}
