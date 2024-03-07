//$Id$
package com.taskswift.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskswift.main.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Long> {

}
