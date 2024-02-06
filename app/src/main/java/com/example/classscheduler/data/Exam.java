package com.example.classscheduler.data;

import java.util.Date;

public class Exam extends Task {

    private DateAndTime examEndTime;

    public Exam(String name, DateAndTime examStartTime, DateAndTime examEndTime, String course) {
        super(name, examStartTime, course);
        this.examEndTime = examEndTime;
    }

    public Exam(String name) {
        this(name, new DateAndTime(), new DateAndTime(), "");
    }

    public DateAndTime getExamEndTime() {
        return examEndTime;
    }

}