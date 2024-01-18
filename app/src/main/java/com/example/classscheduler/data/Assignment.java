package com.example.classscheduler.data;

public class Assignment extends Task {
    private String courseName;

    private String dueDate;

    public Assignment(String assignmentName, String dueDate, String courseName) {
        super(assignmentName);
        this.dueDate = dueDate;
        this.courseName = courseName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String newDueDate) {
        dueDate = newDueDate;
    }
}
