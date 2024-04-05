//$Id$
package com.taskswift.main.dao;

import java.time.LocalDate;
import java.util.List;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.model.TaskCreation;

public interface TaskDao {
	
	public List<Task> getAllTasks();
	
	public Task getTaskById(Long taskId);
	
	public void saveTask(TaskCreation task);

	public void updateTask(TaskCreation taskCreation);
	
	public void deleteTask(Task task);
	
	public void deleteTaskById(Long taskId);

	public List<Task> getTodayTask(LocalDate localDate);

	public List<Task> getCurrentWeekTasks(LocalDate fromDate, LocalDate toDate);

	public void saveCategory(TaskCategory taskCategory);

	public TaskCategory getTaskCategoryByTitle(String taskTitle);

	public List<TaskCategory> getAllTaskCategory();

	public List<Task> getTotalUserTask(Long userId);

	public List<Task> getToDoTasks();

	public List<Task> getHighPriorityTasks();
	
}
