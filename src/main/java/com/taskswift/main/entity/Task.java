//$Id$
package com.taskswift.main.entity;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "task")
public class Task {
	
	@Setter
	@Id
	private Long taskId;
	
	@Setter
	private String taskTitle;
	
	@Setter
	private String taskDesc;
	
	@Setter
	private LocalDate dueDate;

	@Setter
	private String taskPriority;

	@Setter
	private File taskAttachment;

	@Setter
	private String taskRecurring;

	@Setter
	private String taskStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_category")
	@Setter
	private TaskCategory taskCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Setter
	private User user;

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
				", taskCategory=" + taskCategory +
				'}';
	}
}
