//$Id$
package com.taskswift.main.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		Optional<Task> task = taskRepo.findByTaskIdAndTaskIdIsBetween(taskId, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
        return task.orElse(null);
    }

	@Override
	public void saveTask(TaskCreation taskCreation) {

		Task task = getTaskFromTaskCreation(taskCreation);

		TaskCategory taskCategory = getTaskCategoryByTitle(taskCreation.getTaskCategory());
		task.setTaskCategory(taskCategory);

		logger.info(">>> " + task.getTaskTitle() + " Task is getting saved to DB");
		task.setTaskId(TenantUtil.getNextUniqueId());
		taskRepo.save(task);

		List<TaskStatus> statusList = new ArrayList<>();
		for(String statusTitle : taskCreation.getTaskStatusList()){
			TaskStatus taskStatus = new TaskStatus();
			taskStatus.setTaskStatusId(TenantUtil.getNextUniqueId());
			taskStatus.setStatusTitle(statusTitle);
			taskStatus.setTask(task);
			if(statusTitle.equals(taskCreation.getTaskStatus())){
				taskStatus.setSelected(true);
			}
			taskStatusRepo.save(taskStatus);
			statusList.add(taskStatus);
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
		return taskRepo.findAllByDueDateAndTaskIdIsBetween(localDate, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public List<Task> getCurrentWeekTasks(LocalDate fromDate, LocalDate toDate) {
		return taskRepo.findAllByDueDateBetweenAndTaskIdIsBetween(fromDate, toDate, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public void saveCategory(TaskCategory taskCategory) {
		taskCategory.setCategoryId(TenantUtil.getNextUniqueId());
		taskCategoryRepo.save(taskCategory);
	}

	@Override
	public TaskCategory getTaskCategoryByTitle(String taskTitle) {
		return taskCategoryRepo.findByCategoryTitleAndCategoryIdRange(taskTitle, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public List<TaskCategory> getAllTaskCategory() {
		return taskCategoryRepo.findAllByCategoryIdIsBetween(TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	private static Task getTaskFromTaskCreation(TaskCreation taskCreation){
		Task task = new Task();
		task.setTaskTitle(taskCreation.getTaskTitle());
		task.setTaskDesc(taskCreation.getTaskDesc());
		task.setDueDate(taskCreation.getDueDate());
		task.setTaskPriority(taskCreation.getTaskPriority());
		task.setTaskRecurring(taskCreation.getTaskRecurring());
		task.setTaskAttachment(taskCreation.getTaskAttachment());
		return task;
	}

}
