//$Id$
package com.taskswift.main.util;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.taskswift.main.entity.Task;
import com.taskswift.main.exception.TaskException;
import com.taskswift.main.service.TaskService;

@Component
@SuppressWarnings("unchecked")
public class TaskUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskUtil.class);
	
	private static TaskService taskService;
	
	public TaskUtil(TaskService taskService) {
		TaskUtil.taskService = taskService;
	}

	public static JSONObject getAllTasks(){
		JSONObject response = new JSONObject();
		response.put("Task", taskService.getAllTasks());
		return response;
	}
	
	public static JSONObject saveTask(Task task) {
		JSONObject response = new JSONObject();
		try {
			logger.info(">>> Validating Task Inputs");
			validateTask(task);
			
			taskService.saveTask(task);			
			response.put("Task", "Task Created Successfully!!!");
			
		}catch(Exception e) {
			response.put("Task", e.getMessage());
		}		
		
		return response;
	}
	
	public static void validateTask(Task task) {
		
		String taskTitle = task.getTaskTitle();
		String taskDesc = task.getTaskDesc();
		LocalDate taskDueDate = task.getDueDate();
		
		if(taskTitle.isEmpty() || taskTitle.length() > 20) {
			logger.info(">>> Task title has invalid number of characters");
			throw new TaskException(HttpMethod.POST + " :: Invalid Task title length");
		}else if(taskDesc.isEmpty() || taskDesc.length() > 200) {
			logger.info(">>> Task Description has invalid number of characters");
			throw new TaskException(HttpMethod.POST + " :: Invalid Task description length");
		}else if(taskDueDate == null) {
			logger.info(">>> Task Due Date has null value");
			throw new TaskException(HttpMethod.POST + " :: Task Due Date is null");
		}else if(!taskDueDate.isAfter(LocalDate.now()) && !taskDueDate.equals(LocalDate.now())) {
			logger.info(">>> Task Due Date is invalid");
			throw new TaskException(HttpMethod.POST + " :: Task Due Date is invalid");
		}else {
			String titleRegex = Constants.ONLYALPHANUMERIC;
			Pattern pattern = Pattern.compile(titleRegex);
			Matcher matcher = pattern.matcher(taskTitle);
			
			if(!matcher.matches()) {
				logger.info(">>> Task Title is invalid");
				throw new TaskException(HttpMethod.POST + " :: Task Title is invalid");
			}
		}
		
	}

}
