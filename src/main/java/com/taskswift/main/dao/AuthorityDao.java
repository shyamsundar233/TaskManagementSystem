//$Id$
package com.taskswift.main.dao;

import java.util.List;

import com.taskswift.main.entity.Authority;

public interface AuthorityDao {
	
	public void saveAuthority(Authority authority);
	
	public List<Authority> getAuthorities();
	
	public List<String> getRoles();
	
}
