//$Id$
package com.taskswift.main.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskswift.main.dao.TaskDao;
import com.taskswift.main.entity.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public List<Task> getAllTasks() {
		logger.info(">>> All tasks are getting fetched from DB");
		return taskDao.getAllTasks();
	}

	@Override
	public Task getTaskById(Long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveTask(Task task) {
		
		taskDao.saveTask(task);

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
