package com.example.amtvregistrationdemo;

public class Task {
    private String description;
    private User assignedMember;
    private Boolean completed;
    private int points;

    public Task(String description, User assignedMember, int points) {
        this.description = description;
        this.assignedMember = assignedMember;
        this.points = points;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public User getAssignedMember() {
        return assignedMember;
    }

    public int getPoints() {
        return points;
    }
}
