package com.example.classscheduler.data;

public class Course extends Task {
    private String courseTime;
    private String instructorName;

    public Course(String courseName, String courseTime, String instructorName) {
        super(courseName);
        this.courseTime = courseTime;
        this.instructorName = instructorName;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String newCourseTime) {
        courseTime = newCourseTime;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String newInstructorName) {
        instructorName = newInstructorName;
    }
}