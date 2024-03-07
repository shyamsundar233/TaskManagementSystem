//$Id$
package com.taskswift.main.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.taskswift.main.entity.Authority;

public interface AuthorityRepo extends JpaRepository<Authority, Long> {

	@Query("SELECT DISTINCT u.authority FROM Authority u")
    List<String> findDistinctAuthorities();
	
}
