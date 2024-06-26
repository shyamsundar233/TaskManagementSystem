//$Id$
package com.taskswift.main.util;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.entity.User;
import com.taskswift.main.exception.TaskException;
import com.taskswift.main.model.Message;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.model.TaskFilter;
import com.taskswift.main.service.TaskService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

	public static JSONObject getAllTasks(int page, int size){
		JSONObject response = new JSONObject();
		Page<Task> tasksPage = taskService.getAllTasks(page, size);
		List<Task> tasksList = tasksPage.getContent();
		response.put("Task", constructJsonForTask(tasksList).get("result"));
		JSONObject metaData = new JSONObject();
		metaData.put("page", page);
		metaData.put("size", size);
		metaData.put("count", tasksList.size());
		metaData.put("hasMoreRecords", tasksPage.hasNext());
		response.put("metaData", metaData);
		return response;
	}

	public static JSONObject getTaskById(Long taskId){
		JSONObject response = new JSONObject();
		Task task = taskService.getTaskById(taskId);
		if(task != null){
			response.put("Task", constructJsonForTask(new ArrayList<>(Arrays.asList(task))).get("result"));
		}else{
			response.put("Task", new ArrayList<>());
		}
		return response;
	}
	
	public static JSONObject saveTask(TaskCreation taskCreation) {
		JSONObject response = new JSONObject();
		try {
			logger.info(">>> Validating Task Inputs");
			validateTask(taskCreation);

			if(taskCreation.getTaskId() == null){
				taskService.saveTask(taskCreation);
				response.put("Task", "Task Created Successfully!!!");
			}else{
				taskService.updateTask(taskCreation);
				response.put("Task", "Task Updated Successfully!!!");
			}

			NotificationUtil.sendPrivateMessage(getTaskCreationNotf(taskCreation));
			
		}catch(Exception e) {
			response.put("Task", e.getMessage());
		}		
		
		return response;
	}

	public static JSONObject deleteTaskById(Long taskId) {
		JSONObject response = new JSONObject();
		Task task = taskService.getTaskById(taskId);
		taskService.deleteTask(task);
		response.put("Task", "Task Deleted Successfully");
		return response;
	}
	
	public static void validateTask(TaskCreation task) {
		
		String taskTitle = task.getTaskTitle();
		String taskDesc = task.getTaskDesc();
		LocalDate taskDueDate = task.getDueDate();
		
		if(taskTitle.isEmpty() || taskTitle.length() > 200) {
			logger.info(">>> Task title has invalid number of characters");
			throw new TaskException(HttpMethod.POST + " :: Invalid Task title length");
		}else if(taskDesc.isEmpty() || taskDesc.length() > 200) {
			logger.info(">>> Task Description has invalid number of characters");
			throw new TaskException(HttpMethod.POST + " :: Invalid Task description length");
		}else if(taskDueDate == null) {
			logger.info(">>> Task Due Date has null value");
			throw new TaskException(HttpMethod.POST + " :: Task Due Date is null");
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

	public static JSONObject saveTaskCategory(TaskCategory taskCategory) {
		JSONObject response = new JSONObject();
		taskService.saveCategory(taskCategory);
		response.put("SUCCESS", " Task Category Created Successfully!!!");
		return response;
	}

	public static JSONObject getAllTaskCategory(){
		JSONObject response = new JSONObject();
		response.put("TaskCategory", taskService.getAllTaskCategory());
		return response;
	}

	public static JSONObject getAllTasksForCurrentUser(){
		JSONObject response = new JSONObject();
		response.put("totalTasks", constructJsonForTask(taskService.getTotalUserTask(UserUtil.currentUser.getUserid())).get("result"));
		return response;
	}

	public static JSONObject getHomePageElements() {
		JSONObject response = new JSONObject();
		response.put("toDoTasks", constructJsonForTask(taskService.getToDoTasks()).get("result"));
		response.put("weeklyTasks", getAllCurrentWeekTasks().get("weeklyTasks"));
		response.put("highPriorityTasks", constructJsonForTask(taskService.getHighPriorityTasks()).get("result"));
		response.put("todayTask", getAllTodayTasksList().get("todayTask"));
		return response;
	}

	public static JSONObject getFilteredTasks(TaskFilter taskFilter){
		JSONObject response = new JSONObject();
		response.put("Task", constructJsonForTask(taskService.filterTask(taskFilter)).get("result"));
		return response;
	}

	public static JSONObject getTopFiveContributors() {
		JSONObject response = new JSONObject();
		JSONArray usersList = new JSONArray();
		taskService.getTopContributors(5);
		Map<Integer, List<User>> usersMap = taskService.getTopContributors(5);
		for(Map.Entry<Integer, List<User>> entry : usersMap.entrySet()) {
			JSONObject countJson = new JSONObject();
			countJson.put("count", entry.getKey());
			countJson.put("user", constructJsonForUser(entry.getValue()).get("result"));
			usersList.add(countJson);
		}
		response.put("topContributors", usersList);
		return response;
	}

	private static JSONObject constructJsonForUser(List<User> userList) {
		JSONObject response = new JSONObject();
		JSONArray usersJsonArray = new JSONArray();
		for(User user : userList) {
			JSONObject userJson = new JSONObject();
			userJson.put("userId", user.getUserid());
			userJson.put("username", user.getUsername());
			userJson.put("email", user.getEmail());
			usersJsonArray.add(userJson);
		}
		response.put("result", usersJsonArray);
		return response;
	}

	private static Message getTaskCreationNotf(TaskCreation taskCreation) {
		Message message = new Message();
		JSONObject response = new JSONObject();
		response.put("title", "Task Created for you");
		response.put("subject", taskCreation.getTaskTitle());
		response.put("body", taskCreation.getTaskDesc());
		message.setMessageBody(response);
		message.setTo(UserUtil.getUserById(taskCreation.getUserId()).getUsername());
		return message;
	}

	private static List<LocalDate> getStartAndEndOfCurrentWeek() {
		LocalDate currentDate = LocalDate.now();
		LocalDate startDateOfWeek = currentDate.minusDays(currentDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
		LocalDate endDateOfWeek = startDateOfWeek.plusDays(6);
		return new ArrayList<>(Arrays.asList(startDateOfWeek, endDateOfWeek));
	}

	private static JSONObject constructJsonForTask(List<Task> tasksList){
		JSONObject resultTaskJson= new JSONObject();
		JSONArray taskArrJson = new JSONArray();
		for(Task task : tasksList){
			JSONObject taskObj = new JSONObject();
			taskObj.put("taskId", task.getTaskId());
			taskObj.put("taskTitle", task.getTaskTitle());
			taskObj.put("taskDesc", task.getTaskDesc());
			taskObj.put("dueDate", task.getDueDate());
			taskObj.put("taskPriority", task.getTaskPriority());
			taskObj.put("taskCategory", task.getTaskCategory().getCategoryTitle());
			taskObj.put("taskAttachment", task.getTaskAttachment());
			taskObj.put("taskRecurring", task.getTaskRecurring());
			taskObj.put("taskStatus", task.getTaskStatus());
			JSONObject userJson = new JSONObject();
			userJson.put("username", task.getUser().getUsername());
			userJson.put("email", task.getUser().getEmail());
			userJson.put("userId", task.getUser().getUserid());
			taskObj.put("user", userJson);
			taskArrJson.add(taskObj);
		}
		resultTaskJson.put("result", taskArrJson);
		return resultTaskJson;
	}

}
