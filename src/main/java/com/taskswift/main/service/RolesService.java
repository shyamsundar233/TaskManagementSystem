//$Id$
package com.taskswift.main.service;

import com.taskswift.main.entity.Roles;

import java.util.List;

public interface RolesService {
	
	public List<String> getAllRoles();

	public void saveRoles(Roles roles);

	public boolean isRolesPopulated();


}
