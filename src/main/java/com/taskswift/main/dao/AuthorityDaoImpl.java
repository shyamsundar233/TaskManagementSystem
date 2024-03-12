//$Id$
package com.taskswift.main.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taskswift.main.entity.Authority;
import com.taskswift.main.repo.AuthorityRepo;

@Repository
@SuppressWarnings("null")
public class AuthorityDaoImpl implements AuthorityDao {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorityDaoImpl.class);

	@Autowired
	private AuthorityRepo authorityRepo;
	
	@Override
	public void saveAuthority(Authority authority) {
		authorityRepo.save(authority);
		logger.info(">>> Authority saved in DB");
		
	}

	@Override
	public List<Authority> getAuthorities() {		
		return null;
	}

	@Override
	public List<String> getRoles() {
		logger.info(">>> Fetching distinct roles from DB");
		return authorityRepo.findDistinctAuthorities();
	}

	@Override
	public Authority getAuthorityForUser(String username) {
		return authorityRepo.findByUsername(username);
	}

}
