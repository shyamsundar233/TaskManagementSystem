//$Id$
package com.taskswift.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskswift.main.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

}
