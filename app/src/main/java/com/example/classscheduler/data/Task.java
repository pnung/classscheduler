package com.example.classscheduler.data;

import java.util.Comparator;

public class Task {
    private String name;
    private String description;
    private DateAndTime primaryDateAndTime;

    public Task(String name, String description, DateAndTime dateAndTime) {
        System.out.println("creating task");
        this.name = name;
        this.description = description;
        this.primaryDateAndTime = dateAndTime;
    }

    public Task(String name) {
        this(name, "", new DateAndTime());
    }

    @Override
    public String toString() {
        return String.format("name: %s, description: %s", name, description);
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
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
    public void setDescription(String newDescription) {
        description = newDescription;
    }



    public static class AlphabeticalSort implements Comparator<Task> {

        @Override
        public int compare(Task o1, Task o2) {
            return o1.name.toLowerCase().compareTo(o2.name.toLowerCase());
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
                    return -1;
                } else {
                    return 1;
                }
            } else if (o1.primaryDateAndTime.getHour() != o2.primaryDateAndTime.getHour()) {
                return o1.primaryDateAndTime.getHour() - o2.primaryDateAndTime.getHour();
            } else {
                return o1.primaryDateAndTime.getMinute() - o2.primaryDateAndTime.getMinute();
            }
        }
    }
}