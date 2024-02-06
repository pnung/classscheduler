package com.example.classscheduler.data;

public class Assignment extends Task {
    private String courseName;

    public Assignment(String assignmentName, String courseName) {
        super(assignmentName);
        this.courseName = courseName;
    }

    public Assignment(String name, String description, DateAndTime dateAndTime, String course) {
        super(name, description, dateAndTime);
        this.courseName = course;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String newCourseName) {
        courseName = newCourseName;
    }
}
