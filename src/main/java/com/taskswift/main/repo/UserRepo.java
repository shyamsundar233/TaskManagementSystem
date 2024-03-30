//$Id$
package com.taskswift.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskswift.main.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepo extends JpaRepository<User, String> {

    @Query("SELECT u from User u WHERE u.username=?1")
    User getUserByName(String username);

    @Query("SELECT u FROM User u WHERE u.userid = ?1 AND u.userid BETWEEN ?2 AND ?3")
    User findUserByUseridWithinRange(Long userIdToFind, Long startRange, Long endRange);

    List<User> findAllByUseridBetween(Long startRange, Long endRange);

}
