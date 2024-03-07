//$Id$
package com.taskswift.main.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taskswift.main.repo.RolesRepo;

@Repository
public class RolesDaoImpl implements RolesDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RolesDaoImpl.class);

	@Autowired
	private RolesRepo rolesRepo;
	
	@Override
	public List<String> getAllRoles() {
		logger.info(">>> Roles fetched from DB");
		return rolesRepo.getAllRoles();
	}

}
