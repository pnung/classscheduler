package com.example.classscheduler.data;

public class Assignment extends Task {
    private String courseName;

    private String dueDate;

    public Assignment(String assignmentName, String courseName, String dueDate) {
        super(assignmentName);
        this.courseName = courseName;
        this.dueDate = dueDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String newCourseName) {
        courseName = newCourseName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String newDueDate) {
        dueDate = newDueDate;
    }
}
