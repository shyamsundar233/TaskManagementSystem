package com.taskswift.main.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TaskCategory {

    @Id
    private Long categoryId;

    private String categoryTitle;

    private String categoryDesc;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    @Override
    public String toString() {
        return "TaskCategory{" +
                "categoryId=" + categoryId +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", categoryDesc='" + categoryDesc + '\'' +
                '}';
    }
}
