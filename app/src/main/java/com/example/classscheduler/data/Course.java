package com.example.classscheduler.data;

public class Course extends Task {
    private String courseTime;
    private String instructorName;

    public Course(String courseName, String courseTime, String instructorName) {
        super(courseName);
        this.courseTime = courseTime;
        this.instructorName = instructorName;
    }
}