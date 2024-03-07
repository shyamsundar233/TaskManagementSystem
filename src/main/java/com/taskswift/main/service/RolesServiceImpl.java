//$Id$
package com.taskswift.main.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taskswift.main.dao.RolesDao;

@Repository
public class RolesServiceImpl implements RolesService {
	
	private static final Logger logger = LoggerFactory.getLogger(RolesServiceImpl.class);

	@Autowired
	private RolesDao rolesDao;
	
	@Override
	public List<String> getAllRoles() {
		logger.info(">>> Fetching roles from DB");
		return rolesDao.getAllRoles();
	}

}
