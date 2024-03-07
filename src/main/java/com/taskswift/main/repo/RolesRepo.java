//$Id$
package com.taskswift.main.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taskswift.main.entity.Roles;

public interface RolesRepo extends JpaRepository<Roles, Long>{
	
	@Query("SELECT roleName FROM Roles")
	public List<String> getAllRoles();

}
