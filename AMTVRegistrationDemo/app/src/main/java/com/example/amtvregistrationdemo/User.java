package com.example.amtvregistrationdemo;

import java.util.ArrayList;

public class User {
    private final String name, email, role;
    private final ArrayList<Task>tasks;

    public User(String name, String email, Boolean admin) {
        this.name = name;
        this.email = email;

        if(admin) {
            role = "Admin";
        } else {
            role = "Member";
        }

        tasks = new ArrayList<Task>();
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
