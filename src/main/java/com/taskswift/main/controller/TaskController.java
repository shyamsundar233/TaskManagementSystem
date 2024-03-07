//$Id$
package com.taskswift.main.controller;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.taskswift.main.entity.Task;
import com.taskswift.main.util.TaskUtil;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("http://localhost:3000")
public class TaskController {
	
	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	@GetMapping("/tasks")
	public JSONObject getTasks(){
		logger.info(">>> In GET endpoint of Task API for fetching all tasks");
		return TaskUtil.getAllTasks();
	}

	@PostMapping("/tasks")
	public JSONObject postTasks(@RequestBody Task task) {
		logger.info(">>> In POST endpoint of Task API for task creation");
		return TaskUtil.saveTask(task);		
	}

}
