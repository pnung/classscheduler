package com.example.classscheduler.data;

public class Exam extends Task {

    private String examDate;
    private String examStartTime;
    private String examEndTime;
    private String examDuration;

    public Exam(String name, String examDate, String examStartTime, String examEndTime, String examDuration) {
        super(name);
        this.examDate = examDate;
        this.examStartTime = examStartTime;
        this.examDuration = examDuration;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String newExamDate) {
        examDate = newExamDate;
    }

    public String getExamStartTime() {
        return examStartTime;
    }

    public void setExamStartTime(String newExamStartTime) {
        examStartTime = newExamStartTime;
    }

    public String getExamEndTime() {
        return examEndTime;
    }

    public void setExamEndTime(String newExamEndTime) {
        examEndTime = newExamEndTime;
    }

}

