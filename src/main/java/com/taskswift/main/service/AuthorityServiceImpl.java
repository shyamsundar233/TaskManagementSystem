//$Id$
package com.taskswift.main.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskswift.main.dao.AuthorityDao;
import com.taskswift.main.entity.Authority;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	
	
	private static Logger logger = LoggerFactory.getLogger(AuthorityServiceImpl.class);

	@Autowired
	private AuthorityDao authorityDao;
	
	@Override
	public String saveAuthority(Authority authority) {
		logger.info(">>> Saving Authority to DB");
		authorityDao.saveAuthority(authority);
		return "SUCCESS";
	}

	@Override
	public List<Authority> getAuthorities() {
		return authorityDao.getAuthorities();
	}

	@Override
	public List<String> getRoles() {
		return authorityDao.getRoles();
	}

}
