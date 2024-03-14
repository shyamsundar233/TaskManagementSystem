//$Id$
package com.taskswift.main.dao;

import java.time.LocalDate;
import java.util.List;

import com.taskswift.main.entity.Task;

public interface TaskDao {
	
	public List<Task> getAllTasks();
	
	public Task getTaskById(Long taskId);
	
	public void saveTask(Task task);
	
	public void deleteTask(Task task);
	
	public void deleteTaskById(Long taskId);

	public List<Task> getTodayTask(LocalDate localDate);
	
}
