package com.example.focustimer.user;

public class Goal {
    private String name;
    private long totalTimeSpent; // You can use long to represent time in milliseconds or choose a suitable data type

    // Constructor to initialize a goal
    public Goal(String name) {
        this.name = name;
        this.totalTimeSpent = 0; // Initialize total time spent to 0
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Setter method for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for total time spent
    public long getTotalTimeSpent() {
        return totalTimeSpent;
    }

    // Method to add time spent to the goal
    public void addTimeSpent(long time) {
        totalTimeSpent += time;
    }

    // Method to reset the total time spent to 0
    public void resetTimeSpent() {
        totalTimeSpent = 0;
    }
}
