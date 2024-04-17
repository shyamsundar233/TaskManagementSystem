//$Id$
package com.taskswift.main.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.entity.User;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.model.TaskFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.taskswift.main.dao.TaskDao;
import com.taskswift.main.entity.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public Page<Task> getAllTasks(int page, int size) {
		logger.info(">>> All tasks are getting fetched from DB");
		return taskDao.getAllTasks(page, size);
	}

	@Override
	public Task getTaskById(Long taskId) {
		logger.info(">>> {} tasks are getting fetched from DB", taskId);
		return taskDao.getTaskById(taskId);
	}

	@Override
	public void saveTask(TaskCreation task) {
		
		taskDao.saveTask(task);

	}

	@Override
	public void updateTask(TaskCreation taskCreation) {
		taskDao.updateTask(taskCreation);
	}

	@Override
	public void deleteTask(Task task) {
		taskDao.deleteTask(task);
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

	@Override
	public List<Task> getTotalUserTask(Long userId) {
		logger.info(">>> {} tasks fetching from DB", userId);
		return taskDao.getTotalUserTask(userId);
	}

	@Override
	public List<Task> getToDoTasks() {
		return taskDao.getToDoTasks();
	}

	@Override
	public List<Task> getHighPriorityTasks() {
		return taskDao.getHighPriorityTasks();
	}

	@Override
	public List<Task> filterTask(TaskFilter taskFilter) {
		return taskDao.filterTask(taskFilter);
	}

	@Override
	public Map<Integer, List<User>> getTopContributors(int count) {
		return taskDao.getTopContributors(count);
	}

}
