//$Id$
package com.taskswift.main.service;

import java.util.List;

import com.taskswift.main.entity.Authority;

public interface AuthorityService {
	
	public String saveAuthority(Authority authority);
	
	public List<Authority> getAuthorities();
	
	public List<String> getRoles();

	public Authority getAuthorityForUser(String username);
	
}
