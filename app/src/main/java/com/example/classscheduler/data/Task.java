package com.example.classscheduler.data;

import java.util.ArrayList;

public class Task {
    private String name;
    private String description;

    private static ArrayList<Task> taskList = new ArrayList<>();

    public Task(String name, String description) {
        System.out.println("making Task");
        this.name = name;
        this.description = description;

        taskList.add(this);
    }

    public Task(String name) {
        this(name, "");
    }

    @Override
    public String toString() {
        return String.format("name: %s, description: %s", name, description);
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public void setName(String newName) {
        name = newName;
    }
    public void setDescription(String newDescription) {
        description = newDescription;
    }

    public static ArrayList<Task> getTaskList() {
        return taskList;
    }

}