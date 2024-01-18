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

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String newExamDate) {
        examDate = newExamDate;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String newExamTime) {
        examTime = newExamTime;
    }

    public String getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(String newExamDuration) {
        examDuration = newExamDuration;
    }
}
