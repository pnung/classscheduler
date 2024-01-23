package com.example.classscheduler.data;

import java.util.Comparator;
import java.time.*;

public class DateAndTime {
    int year;
    int month;
    int day;
    int hour;
    int minute;
    boolean PM;


    public DateAndTime(int year, int month, int day, boolean pm, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.PM = pm;
        this.hour = hour;
        this.minute = minute;
        System.out.printf("creating DateAndTime with day: %d\n", day);
    }

    public DateAndTime() {
        this(0,0,0,false, 0,0);
        System.out.println("no arg DateAndTime");
    }

    public DateAndTime(int month, int day) {
        this(0, month, day, false, 0, 0);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isPM() {
        return PM;
    }

    public void setPM(boolean PM) {
        this.PM = PM;
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d/%d:%d%d",year,month,day,hour,minute, PM ? "PM" : "AM");
    }

    public String getCardTime() {
        return String.format("%d/%d at %d:%s%s", month, day, hour, minute >= 10 ? minute : "0" + minute, isPM() ? "PM" : "AM");
    }

}
