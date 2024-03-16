package com.taskswift.main.entity;

import jakarta.persistence.*;

@Entity
public class TaskStatus {

    @Id
    private Long taskStatusId;

    private String statusTitle;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

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

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    @Override
    public String toString() {
        return "TaskStatus{" +
                "taskStatusId=" + taskStatusId +
                ", statusTitle=" + statusTitle +
                '}';
    }
}
