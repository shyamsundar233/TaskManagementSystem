//$Id$
package com.taskswift.main.controller;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskCategory;
import com.taskswift.main.model.TaskCreation;
import com.taskswift.main.model.TaskFilter;
import com.taskswift.main.util.TaskUtil;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")

public class TaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@GetMapping("/tasks")
	public JSONObject getTasks(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size){
		logger.info(">>> In GET endpoint of Task API for fetching all tasks");
		return TaskUtil.getAllTasks(page, size);
	}

	@PostMapping("/tasks")
	public JSONObject postTasks(@RequestBody TaskCreation task) {
		logger.info(">>> In POST endpoint of Task API for task creation");
		return TaskUtil.saveTask(task);		
	}

	@GetMapping("/tasks/{taskId}")
	public JSONObject getTaskById(@PathVariable Long taskId){
		logger.info(">>> In POST endpoint of Task By Id API for task creation");
		return TaskUtil.getTaskById(taskId);
	}

	@DeleteMapping("/tasks/{taskId}")
	public JSONObject deleteTaskById(@PathVariable Long taskId){
		logger.info(">>> In DELETE endpoint of Task By Id API for task deletion");
		return TaskUtil.deleteTaskById(taskId);
	}

	@GetMapping("/todayTask")
	public JSONObject getTodayTask(){
		logger.info(">>> In GET endpoint of Today Task API");
		return TaskUtil.getAllTodayTasksList();
	}

	@GetMapping("/currentWeekTask")
	public JSONObject getCurrentWeekTask(){
		logger.info(">>> In GET endpoint of Current Week Task API");
		return TaskUtil.getAllCurrentWeekTasks();
	}

	@GetMapping("/homePage")
	public JSONObject getAllHomePageElements() {
		logger.info(">>> In GET endpoint of Home Page Task API");
		return TaskUtil.getHomePageElements();
	}

	@PostMapping("/taskFilter")
	public JSONObject postTaskFilter(@RequestBody TaskFilter taskFilter){
		logger.info(">>> In POST endpoint of Filter Task API");
		return TaskUtil.getFilteredTasks(taskFilter);
	}

	@GetMapping("/totalTask")
	public JSONObject getTotalTask() {
		return TaskUtil.getAllTasksForCurrentUser();
	}

	@GetMapping("/taskCategory")
	public JSONObject getTaskCategory(){
		return TaskUtil.getAllTaskCategory();
	}

	@PostMapping("/taskCategory")
	public JSONObject postTaskCategory(@RequestBody TaskCategory taskCategory){
		return TaskUtil.saveTaskCategory(taskCategory);
	}

}
