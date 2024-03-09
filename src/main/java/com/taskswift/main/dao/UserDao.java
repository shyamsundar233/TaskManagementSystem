//$Id$
package com.taskswift.main.dao;

import com.taskswift.main.entity.User;

public interface UserDao {

	public User getUserByName(String name);
	public void saveUser(User user);

}
