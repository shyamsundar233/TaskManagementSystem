//$Id$
package com.taskswift.main.util;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskStatus;
import com.taskswift.main.exception.TaskException;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.service.TaskService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		response.put("Task", constructJsonForTask(taskService.getAllTasks()).get("result"));
		return response;
	}
	
	public static JSONObject saveTask(TaskCreation taskCreation) {
		JSONObject response = new JSONObject();
		try {
			logger.info(">>> Validating Task Inputs");
			validateTask(taskCreation);
			
			taskService.saveTask(taskCreation);
			response.put("Task", "Task Created Successfully!!!");
			
		}catch(Exception e) {
			response.put("Task", e.getMessage());
		}		
		
		return response;
	}
	
	public static void validateTask(TaskCreation task) {
		
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

	public static JSONObject getAllTodayTasksList(){
		JSONObject response = new JSONObject();
		List<Task> todaysTaskList = taskService.getTodayTask(LocalDate.now());
		JSONObject taskJson = constructJsonForTask(todaysTaskList);
		response.put("todayTask", taskJson.get("result"));
		return response;
	}

	public static JSONObject getAllCurrentWeekTasks(){
		JSONObject response = new JSONObject();
		List<LocalDate> currentWeek = getStartAndEndOfCurrentWeek();
		List<Task> currentWeekTasksList = taskService.getCurrentWeekTasks(currentWeek.get(0), currentWeek.get(1));
		JSONObject tasksJson = constructJsonForTask(currentWeekTasksList);
		response.put("weeklyTasks", tasksJson.get("result"));
		return response;
	}

	private static List<LocalDate> getStartAndEndOfCurrentWeek() {
		LocalDate currentDate = LocalDate.now();
		LocalDate startDateOfWeek = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
		LocalDate endDateOfWeek = startDateOfWeek.plusDays(6);
		return new ArrayList<>(Arrays.asList(startDateOfWeek, endDateOfWeek));
	}

	private static List<String> getTaskStatusList(List<TaskStatus> taskStatusList){
		List<String> statusList = new ArrayList<>();
		for(TaskStatus taskStatus : taskStatusList){
			statusList.add(taskStatus.getStatusTitle());
		}
		return statusList;
	}

	private static JSONObject constructJsonForTask(List<Task> tasksList){
		JSONObject resultTaskJson= new JSONObject();
		JSONArray taskArrJson = new JSONArray();
		for(Task task : tasksList){
			JSONObject taskObj = new JSONObject();
			taskObj.put("taskTitle", task.getTaskTitle());
			taskObj.put("taskDesc", task.getTaskDesc());
			taskObj.put("dueDate", task.getDueDate());
			taskObj.put("taskPriority", task.getTaskPriority());
			taskObj.put("taskCategory", task.getTaskCategory());
			taskObj.put("taskAttachment", task.getTaskAttachment());
			taskObj.put("taskRecurring", task.getTaskRecurring());
			taskObj.put("taskStatus", task.getTaskStatus().getStatusTitle());
			taskObj.put("taskStatusList", getTaskStatusList(task.getTaskStatusList()));
			taskArrJson.add(taskObj);
		}
		resultTaskJson.put("result", taskArrJson);
		return resultTaskJson;
	}

}
