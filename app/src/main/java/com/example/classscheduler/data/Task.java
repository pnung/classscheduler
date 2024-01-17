package com.example.classscheduler.data;

public class Task {
    private String name;
    private String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name) {
        this(name, "");
    }
}
