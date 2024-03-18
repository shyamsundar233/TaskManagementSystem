//$Id$
package com.taskswift.main.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.entity.TaskStatus;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.repo.TaskCategoryRepo;
import com.taskswift.main.repo.TaskStatusRepo;
import com.taskswift.main.util.TenantUtil;
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

	@Autowired
	private TaskStatusRepo taskStatusRepo;

	@Autowired
	private TaskCategoryRepo taskCategoryRepo;

	@Override
	public List<Task> getAllTasks() {
		logger.info(">>> Tasks fetched from DB");
		return taskRepo.findAllByTaskIdIsBetween(TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public Task getTaskById(Long taskId) {
		logger.info(">>> " + taskId + " Task is getting fetched from DB");
		return null;
	}

	@Override
	public void saveTask(TaskCreation taskCreation) {

		Task task = getTaskFromTaskCreation(taskCreation);

		taskCategoryRepo.save(task.getTaskCategory());

		logger.info(">>> " + task.getTaskTitle() + " Task is getting saved to DB");
		task.setTaskId(TenantUtil.getNextUniqueId());
		taskRepo.save(task);

		List<TaskStatus> statusList = new ArrayList<>();
		for(String statusTitle : taskCreation.getTaskStatusList()){
			TaskStatus taskStatus = new TaskStatus();
			taskStatus.setTaskStatusId(TenantUtil.getNextUniqueId());
			taskStatus.setStatusTitle(statusTitle);
			taskStatus.setTask(task);
			taskStatusRepo.save(taskStatus);
			statusList.add(taskStatus);
			if(statusTitle.equals(taskCreation.getTaskStatus())){
				task.setTaskStatus(taskStatus);
			}
		}
		task.setTaskStatusList(statusList);
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

	@Override
	public List<Task> getTodayTask(LocalDate localDate) {
		return taskRepo.findAllByDueDate(localDate);
	}

	@Override
	public List<Task> getCurrentWeekTasks(LocalDate fromDate, LocalDate toDate) {
		return taskRepo.findAllByDueDateBetween(fromDate, toDate);
	}

	private static Task getTaskFromTaskCreation(TaskCreation taskCreation){
		Task task = new Task();
		task.setTaskTitle(taskCreation.getTaskTitle());
		task.setTaskDesc(taskCreation.getTaskDesc());
		task.setDueDate(taskCreation.getDueDate());
		task.setTaskPriority(taskCreation.getTaskPriority());
		task.setTaskRecurring(taskCreation.getTaskRecurring());
		task.setTaskAttachment(taskCreation.getTaskAttachment());
		TaskCategory taskCategory = new TaskCategory();
		taskCategory.setCategoryId(TenantUtil.getNextUniqueId());
		taskCategory.setCategoryTitle(taskCreation.getTaskCategory());
		task.setTaskCategory(taskCategory);
		return task;
	}

}
