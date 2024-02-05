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

    public static int getMonthNumberFromString(String m) {
        switch(m) {
            case "Jan": return 1;
            case "Feb": return 2;
            case "Mar": return 3;
            case "Apr": return 4;
            case "May": return 5;
            case "Jun": return 6;
            case "Jul": return 7;
            case "Aug": return 8;
            case "Sep": return 9;
            case "Oct": return 10;
            case "Nov": return 11;
            case "Dec": return 12;
            default: return 0;
        }
    }

    public static String getMonthStringFromNumber(int n) {
        switch(n) {
            case 1: return "Jan";
            case 2: return "Feb";
            case 3: return "Mar";
            case 4: return "Apr";
            case 5: return "May";
            case 6: return "Jun";
            case 7: return "Jul";
            case 8: return "Aug";
            case 9: return "Sep";
            case 10: return "Oct";
            case 11: return "Nov";
            case 12: return "Dec";
            default: return "";
        }
    }

    public static int getNumberOfDays(String m) {
        switch(m) {
            case "Feb": return 28;
            case "Sep": return 30;
            case "Apr": return 30;
            case "Jun": return 30;
            case "Nov": return 30;
            default: return 31;
        }
    }

    @Override
    public String toString() {
        return String.format("%d/%d/%d/%d:%d%d",year,month,day,hour,minute, PM ? "PM" : "AM");
    }

    public String getCardTime() {
        return String.format("%s. %d at %d:%s%s", getMonthStringFromNumber(month), day, hour, minute >= 10 ? minute : "0" + minute, isPM() ? "PM" : "AM");
    }

    public String getDateString() {
        return String.format("%s. %d", getMonthStringFromNumber(month), day);
    }

    public String getTimeString() {
        return String.format("%d:%s%s", hour, minute >= 10 ? minute : "0" + minute, isPM() ? "PM" : "AM");
    }

}
