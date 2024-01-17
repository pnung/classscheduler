package com.example.classscheduler.data;

import java.util.ArrayList;

public class Task {
    private String name;
    private String description;

    private static ArrayList<Task> taskList;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;

        taskList.add(this);
    }

    public Task(String name) {
        this(name, "");
    }
}