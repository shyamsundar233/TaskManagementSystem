package com.taskswift.main.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class TaskFilter {

    private List<String> title;

    private List<String> description;

    private List<String> dueDate;

    private List<String> status;

    private List<String> category;

    private List<String> priority;

    private List<String> recurring;

    public TaskFilter() {
        this.title = new ArrayList<>();
        this.description = new ArrayList<>();
        this.dueDate = new ArrayList<>();
        this.status = new ArrayList<>();
        this.category = new ArrayList<>();
        this.priority = new ArrayList<>();
        this.recurring = new ArrayList<>();
    }

    public TaskFilter(List<String> title, List<String> description, List<String> dueDate, List<String> status, List<String> category, List<String> priority, List<String> recurring) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.category = category;
        this.priority = priority;
        this.recurring = recurring;
    }
}
