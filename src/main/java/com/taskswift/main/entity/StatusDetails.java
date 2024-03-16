package com.taskswift.main.entity;

import jakarta.persistence.*;

@Entity
public class StatusDetails {

    @Id
    private Long statusId;

    private String statusTitle;

    private String statusDesc;

    @OneToOne(mappedBy = "statusDetails")
    private TaskStatus taskStatus;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return "StatusDetails{" +
                "statusId=" + statusId +
                ", statusTitle='" + statusTitle + '\'' +
                ", statusDesc='" + statusDesc + '\'' +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
