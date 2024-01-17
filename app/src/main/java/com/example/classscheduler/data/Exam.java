package com.example.classscheduler.data;

public class Exam extends Task {

    private String examDate;
    private String examTime;
    private String examDuration;

    public Exam(String name, String examDate, String examTime, String examDuration) {
        super(name);
        this.examDate = examDate;
        this.examTime = examTime;
        this.examDuration = examDuration;
    }

}
