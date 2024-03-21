package com.taskswift.main.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class TaskTimeline {

    @Id
    private Long activityId;

    private String message;

    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "TaskTimeline{" +
                "activityId=" + activityId +
                ", message='" + message + '\'' +
                ", createdTime=" + createdTime +
                ", task=" + task.getTaskId() +
                '}';
    }
}
