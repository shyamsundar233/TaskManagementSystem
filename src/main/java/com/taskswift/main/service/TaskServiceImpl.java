//$Id$
package com.taskswift.main.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.util.TimelineUtil;
import com.taskswift.main.util.UserUtil;
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
		logger.info(">>> {} tasks are getting fetched from DB", taskId);
		return taskDao.getTaskById(taskId);
	}

	@Override
	public void saveTask(TaskCreation task) {
		
		Task taskObj = taskDao.saveTask(task);
		String message = "Task Created by " + UserUtil.currentUser.getUsername() + " On " + LocalDateTime.now();
		TimelineUtil.addActivityToTimeline(message, taskObj.getTaskId());

	}

	@Override
	public void deleteTask(Task task) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTaskById(Long taskId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Task> getTodayTask(LocalDate localDate) {
		return taskDao.getTodayTask(localDate);
	}

	@Override
	public List<Task> getCurrentWeekTasks(LocalDate fromDate, LocalDate toDate) {
		return taskDao.getCurrentWeekTasks(fromDate, toDate);
	}

	@Override
	public void saveCategory(TaskCategory taskCategory) {
		taskDao.saveCategory(taskCategory);
	}

	@Override
	public List<TaskCategory> getAllTaskCategory() {
		return taskDao.getAllTaskCategory();
	}

}
