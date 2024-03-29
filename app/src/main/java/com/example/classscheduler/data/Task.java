package com.example.classscheduler.data;

import java.util.Comparator;

public class Task {
    private String name;
    private DateAndTime primaryDateAndTime;
    private String course;

    public Task(String name, DateAndTime dateAndTime, String course) {
        System.out.println("creating task");
        this.name = name;
        this.primaryDateAndTime = dateAndTime;
        this.course = course;
    }

    public Task(String name) {
        this(name, new DateAndTime(), "");
    }

    @Override
    public String toString() {
        return String.format("name: %s", name);
    }

    public String getName() {
        return name;
    }

    public String getCardTime() {
        return primaryDateAndTime.getCardTime();
    }

    public String getType() {
        return "Todo Item";
    }

    public void setName(String newName) {
        name = newName;
    }

    public DateAndTime getPrimaryDateAndTime() {
        return primaryDateAndTime;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public static class AlphabeticalSort implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            return o1.name.toLowerCase().compareTo(o2.name.toLowerCase());
        }
    }

    public static class CourseSort implements Comparator<Task> {
        @Override
        public int compare(Task o1, Task o2) {
            int compareResult = o1.course.toLowerCase().compareTo(o2.course.toLowerCase());
            ChronologicalSort backupSort = new ChronologicalSort();
            if (compareResult == 0) {
                return backupSort.compare(o1, o2);
            }
            return compareResult;
        }
    }


    public static class ChronologicalSort implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            System.out.println(o1.primaryDateAndTime.getDay());
            System.out.println(o2.primaryDateAndTime.getDay());
            if (o1.primaryDateAndTime.getYear() != o2.primaryDateAndTime.getYear()) {
                return o1.primaryDateAndTime.getYear() - o2.primaryDateAndTime.getYear();
            } else if (o1.primaryDateAndTime.getMonth() != o2.primaryDateAndTime.getMonth()) {
                return o1.primaryDateAndTime.getMonth() - o2.primaryDateAndTime.getMonth();
            } else if (o1.primaryDateAndTime.getDay() != o2.primaryDateAndTime.getDay()) {
                return o1.primaryDateAndTime.getDay() - o2.primaryDateAndTime.getDay();
            } else if (o1.primaryDateAndTime.isPM() != o2.primaryDateAndTime.isPM()) {
                if (o1.primaryDateAndTime.isPM()) {
                    return 1;
                } else {
                    return -1;
                }
            } else if (o1.primaryDateAndTime.getHour() != o2.primaryDateAndTime.getHour()) {
                return (o1.primaryDateAndTime.getHour() % 12) - (o2.primaryDateAndTime.getHour() % 12);
            } else {
                return o1.primaryDateAndTime.getMinute() - o2.primaryDateAndTime.getMinute();
            }
        }
    }
}