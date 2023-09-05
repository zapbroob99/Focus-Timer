package com.example.focustimer.user;

public class Goal {
    private String name;
    private long totalTimeSpent;
    private long dailyTimeSpent;
    private int goalDuration;

    // Constructor to initialize a goal
    public Goal(String name,int totalTimeSpent,int dailyTimeSpent, int goalDuration) {
        this.name = name;
        this.totalTimeSpent=totalTimeSpent;
        this.dailyTimeSpent=dailyTimeSpent;
        this.goalDuration=goalDuration;
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    public int getGoalDuration() {
        return goalDuration;
    }

    public void setGoalDuration(int goalDuration) {
        this.goalDuration = goalDuration;
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
