//$Id$
package com.taskswift.main.entity;

import java.io.File;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
public class Task {
	
	@Id
	private Long taskId;
	
	private String taskTitle;
	
	private String taskDesc;
	
	private LocalDate dueDate;
	
	private String taskPriority;
	
	private String taskCategory;
	
	private File taskAttachment;
	
	private String taskRecurring;

	@OneToOne(mappedBy = "task")
	private TaskStatus taskStatus;

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

	public String getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
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

	public TaskStatus getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatus taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public String toString() {
		return "Task{" +
				"taskId=" + taskId +
				", taskTitle='" + taskTitle + '\'' +
				", taskDesc='" + taskDesc + '\'' +
				", dueDate=" + dueDate +
				", taskPriority='" + taskPriority + '\'' +
				", taskCategory='" + taskCategory + '\'' +
				", taskAttachment=" + taskAttachment +
				", taskRecurring='" + taskRecurring + '\'' +
				", taskStatus=" + taskStatus +
				'}';
	}
}
