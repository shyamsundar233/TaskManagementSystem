//$Id$
package com.taskswift.main.entity;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task {
	
	@Id
	private Long taskId;
	
	private String taskTitle;
	
	private String taskDesc;
	
	private LocalDate dueDate;
	
	private String taskPriority;
	
	private File taskAttachment;
	
	private String taskRecurring;

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskStatus> taskStatusList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_category")
	private TaskCategory taskCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}

	public File getTaskAttachment() {
		return taskAttachment;
	}

	public void setTaskAttachment(File taskAttachment) {
		this.taskAttachment = taskAttachment;
	}

	public String getTaskRecurring() {
		return taskRecurring;
	}

	public void setTaskRecurring(String taskRecurring) {
		this.taskRecurring = taskRecurring;
	}

	public List<TaskStatus> getTaskStatusList() {
		return taskStatusList;
	}

	public void setTaskStatusList(List<TaskStatus> taskStatusList) {
		this.taskStatusList = taskStatusList;
	}

	public TaskCategory getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(TaskCategory taskCategory) {
		this.taskCategory = taskCategory;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Task{" +
				"taskId=" + taskId +
				", taskTitle='" + taskTitle + '\'' +
				", taskDesc='" + taskDesc + '\'' +
				", dueDate=" + dueDate +
				", taskPriority='" + taskPriority + '\'' +
				", taskAttachment=" + taskAttachment +
				", taskRecurring='" + taskRecurring + '\'' +
				", taskStatusList=" + taskStatusList +
				", taskCategory=" + taskCategory +
				'}';
	}
}
