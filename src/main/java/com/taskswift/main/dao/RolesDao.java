//$Id$
package com.taskswift.main.dao;

import com.taskswift.main.entity.Roles;

import java.util.List;

public interface RolesDao {
	
	public List<String> getAllRoles();

	public void saveRoles(Roles roles);

	public boolean isRolesPopulated();
	
}
