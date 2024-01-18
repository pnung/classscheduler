package com.example.classscheduler.data;

public class Assignment extends Task {
    private String courseName;

    public Assignment(String assignmentName, String courseName) {
        super(assignmentName);
        this.courseName = courseName;
    }

}
