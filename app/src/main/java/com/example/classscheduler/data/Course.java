package com.example.classscheduler.data;

public class Course extends Task {
    private String courseTime;
    private String instructorName;
    private String courseDays;

    public Course(String courseName, String courseTime, String instructorName) {
        super(courseName);
        this.courseTime = courseTime;
        this.instructorName = instructorName;
    }

    public Course(String courseName, String instructorName, String courseDays, String courseTime) {
        super(courseName);
        this.instructorName = instructorName;
        this.courseDays = courseDays;
        this.courseTime = courseTime;

    }

    public String getCourseDays() {
        return courseDays;
    }

    public void setCourseDays(String newCourseDays) {
        this.courseDays = newCourseDays;
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