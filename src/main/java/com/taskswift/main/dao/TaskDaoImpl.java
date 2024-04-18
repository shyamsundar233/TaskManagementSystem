//$Id$
package com.taskswift.main.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;

import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.entity.User;
import com.taskswift.main.exception.TaskException;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.model.TaskFilter;
import com.taskswift.main.repo.TaskCategoryRepo;
import com.taskswift.main.util.Constants;
import com.taskswift.main.util.TenantUtil;
import com.taskswift.main.util.UserUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.taskswift.main.entity.Task;
import com.taskswift.main.repo.TaskRepo;

@Repository
@Transactional
public class TaskDaoImpl implements TaskDao {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskDaoImpl.class);
	
	@Autowired
	private TaskRepo taskRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TaskCategoryRepo taskCategoryRepo;

	@Override
	public Page<Task> getAllTasks(int page, int size) {
		logger.info(">>> Tasks fetched from DB");
		Pageable pageable = PageRequest.of(page, size);
		Page<Task> page1 = taskRepo.findAllByTaskIdIsBetweenOrderByTaskIdDesc(TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange(), pageable);
		return page1;
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

		logger.info(">>> " + task.getTaskTitle() + " Task is getting saved to DB");
		task.setTaskId(TenantUtil.getNextUniqueId());
		taskRepo.save(task);
		
		logger.info(">>> " + task.getTaskId() + " Task is saved in DB");
	}

	@Override
	public void updateTask(TaskCreation taskCreation) {
		Optional<Task> taskOptional = taskRepo.findById(taskCreation.getTaskId());
		if(taskOptional.isEmpty()){
			logger.error("Task not found to update :: TaskId: " + taskCreation.getTaskId());
			throw new TaskException("Task not found to update :: TaskId: " + taskCreation.getTaskId());
		}else{
			Task task = getTaskFromTaskCreation(taskCreation);
			taskRepo.save(task);
		}
	}

	@Override
	public void deleteTask(Task task) {
		taskRepo.delete(task);
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

	@Override
	public List<Task> getTotalUserTask(Long userId) {
		List<Task> tasksList = taskRepo.findAllByUserAndTaskIdIsBetween(UserUtil.getUserById(userId), TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
		logger.info(">>> {} user tasks fetched from DB", userId);
		return tasksList;
	}

	@Override
	public List<Task> getToDoTasks() {
		return taskRepo.findByTaskStatusAndTaskIdIsBetween("To Do", TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public List<Task> getHighPriorityTasks() {
		return taskRepo.findByTaskPriorityAndTaskIdIsBetween("High Priority", TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
	}

	@Override
	public List<Task> filterTask(TaskFilter taskFilter) {
		String queryStr = "SELECT task FROM Task task WHERE ";
		Map<String, String> filterParams = new HashMap<>();
		boolean flag = false;

		if(!taskFilter.getTitle().isEmpty()){
			queryStr = queryStr + "task.taskTitle " + Constants.OPERATORS_MAP.get(taskFilter.getTitle().get(0)) + " :taskTitle";
			filterParams.put("taskTitle", taskFilter.getTitle().get(0).equals("Contains") ? "%" + taskFilter.getTitle().get(1) + "%" : taskFilter.getTitle().get(1));
			flag = true;
		}

		if(!taskFilter.getDescription().isEmpty()){
			if(flag){
				queryStr = queryStr + " " + Constants.OPERATORS_MAP.get("and") + " ";
			}
			queryStr = queryStr + "task.taskDesc " + Constants.OPERATORS_MAP.get(taskFilter.getDescription().get(0)) + " :taskDesc";
			filterParams.put("taskDesc", taskFilter.getDescription().get(0).equals("Contains") ? "%" + taskFilter.getDescription().get(1) + "%" : taskFilter.getDescription().get(1));
			flag = true;
		}

		if(!taskFilter.getDueDate().isEmpty()){
			if(flag){
				queryStr = queryStr + " " + Constants.OPERATORS_MAP.get("and") + " ";
			}
			queryStr = queryStr + "task.dueDate " + Constants.OPERATORS_MAP.get(taskFilter.getDueDate().get(0)) + " :dueDate";
			filterParams.put("dueDate", taskFilter.getDueDate().get(0).equals("Contains") ? "%" + taskFilter.getDueDate().get(1) + "%" : taskFilter.getDueDate().get(1));
			flag = true;
		}

		if(!taskFilter.getStatus().isEmpty()){
			if(flag){
				queryStr = queryStr + " " + Constants.OPERATORS_MAP.get("and") + " ";
			}
			queryStr = queryStr + "task.taskStatus " + Constants.OPERATORS_MAP.get(taskFilter.getStatus().get(0)) + " :taskStatus";
			filterParams.put("taskStatus", taskFilter.getStatus().get(0).equals("Contains") ? "%" + taskFilter.getStatus().get(1) + "%" : taskFilter.getStatus().get(1));
			flag = true;
		}

		if(!taskFilter.getPriority().isEmpty()){
			if(flag){
				queryStr = queryStr + " " + Constants.OPERATORS_MAP.get("and") + " ";
			}
			queryStr = queryStr + "task.taskPriority " + Constants.OPERATORS_MAP.get(taskFilter.getPriority().get(0)) + " :taskPriority";
			filterParams.put("taskPriority", taskFilter.getPriority().get(0).equals("Contains") ? "%" + taskFilter.getPriority().get(1) + "%" : taskFilter.getPriority().get(1));
			flag = true;
		}

		if(!taskFilter.getCategory().isEmpty()){
			if(flag){
				queryStr = queryStr + " " + Constants.OPERATORS_MAP.get("and") + " ";
			}
			queryStr = queryStr + "task.taskCategory " + Constants.OPERATORS_MAP.get(taskFilter.getCategory().get(0)) + " :taskCategory";
			filterParams.put("taskCategory", taskFilter.getCategory().get(0).equals("Contains") ? "%" + taskFilter.getCategory().get(1) + "%" : taskFilter.getCategory().get(1));
			flag = true;
		}

		if(!taskFilter.getRecurring().isEmpty()){
			if(flag){
				queryStr = queryStr + " " + Constants.OPERATORS_MAP.get("and") + " ";
			}
			queryStr = queryStr + "task.taskRecurring " + Constants.OPERATORS_MAP.get(taskFilter.getRecurring().get(0)) + " :taskRecurring";
			filterParams.put("taskRecurring", taskFilter.getRecurring().get(0).equals("Contains") ? "%" + taskFilter.getRecurring().get(1) + "%" : taskFilter.getRecurring().get(1));
		}


		Query query = entityManager.createQuery(queryStr);
		for(Entry<String, String> entry : filterParams.entrySet()){
			if(entry.getKey().equals("dueDate")){
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate date = LocalDate.parse(entry.getValue(), formatter);
				query.setParameter(entry.getKey(), date);
			}else if(entry.getKey().equals("taskCategory")){
				TaskCategory taskCategory = taskCategoryRepo.findByCategoryTitleAndCategoryIdRange(entry.getValue(), TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
				query.setParameter(entry.getKey(), taskCategory);
			}
			else{
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}

        return query.getResultList();
	}

	@Override
	public Map<Integer, List<User>> getTopContributors(int count) {
		String queryStr = "SELECT t.user, COUNT(t) as taskCount " +
							"FROM Task t " +
							"WHERE t.taskId BETWEEN :startRange AND :endRange " +
							"GROUP BY t.user " +
							"ORDER BY taskCount DESC";
		TypedQuery<Object[]> query = entityManager.createQuery(queryStr, Object[].class);
		query.setParameter("startRange", TenantUtil.currentTenant.getStartRange());
		query.setParameter("endRange", TenantUtil.currentTenant.getEndRange());
		query.setMaxResults(5);
		List<Object[]> results = query.getResultList();

		Map<Integer, List<User>> topContributors = new HashMap<>();
		for(Object[] result : results){
			int taskCount = Integer.parseInt(result[1].toString());
			User taskUser = (User) result[0];
			if(topContributors.containsKey(taskCount)){
				topContributors.get(taskCount).add(taskUser);
			}else{
				List<User> contributors = new ArrayList<>();
				contributors.add(taskUser);
				topContributors.put(taskCount, contributors);
			}
			logger.info(topContributors.toString());
		}
		return topContributors;
	}

	private Task getTaskFromTaskCreation(TaskCreation taskCreation){
		Task task = new Task();
		task.setTaskId(taskCreation.getTaskId());
		task.setTaskTitle(taskCreation.getTaskTitle());
		task.setTaskDesc(taskCreation.getTaskDesc());
		task.setTaskStatus(taskCreation.getTaskStatus());
		task.setDueDate(taskCreation.getDueDate());
		task.setTaskPriority(taskCreation.getTaskPriority());
		task.setTaskRecurring(taskCreation.getTaskRecurring());
		task.setTaskAttachment(taskCreation.getTaskAttachment());
		task.setUser(UserUtil.getUserById(taskCreation.getUserId()));
		TaskCategory taskCategory = getTaskCategoryByTitle(taskCreation.getTaskCategory());
		task.setTaskCategory(taskCategory);
		return task;
	}

}
