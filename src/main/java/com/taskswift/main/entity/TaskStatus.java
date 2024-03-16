package com.taskswift.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class TaskStatus {

    @Id
    private Long taskStatusId;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @OneToOne
    @JoinColumn(name = "details_id")
    private StatusDetails statusDetails;

    public Long getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(Long taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public StatusDetails getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(StatusDetails statusDetails) {
        this.statusDetails = statusDetails;
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                "taskStatusId=" + taskStatusId +
                ", task=" + task +
                ", statusDetails=" + statusDetails +
                '}';
    }
}
