//$Id$
package com.taskswift.main.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taskswift.main.entity.Task;
import com.taskswift.main.repo.TaskRepo;

@Repository
public class TaskDaoImpl implements TaskDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);
	
	@Autowired
	private TaskRepo taskRepo;

	@Override
	public List<Task> getAllTasks() {
		logger.info(">>> Tasks fetched from DB");
		return taskRepo.findAll();
	}

	@Override
	public Task getTaskById(Long taskId) {
		logger.info(">>> " + taskId + " Task is getting fetched from DB");
		return null;
	}

	@Override
	public void saveTask(Task task) {
		
		logger.info(">>> " + task.getTaskTitle() + " Task is getting saved to DB");
		
		taskRepo.save(task);
		
		logger.info(">>> " + task.getTaskId() + " Task is saved in DB");
	}

	@Override
	public void deleteTask(Task task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTaskById(Long taskId) {
		// TODO Auto-generated method stub

	}

}
