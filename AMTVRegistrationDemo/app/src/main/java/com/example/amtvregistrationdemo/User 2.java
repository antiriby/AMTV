package com.example.amtvregistrationdemo;

import java.util.ArrayList;

public class User {
    public String firstName, lastName, email;
    public Household household;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.household = new Household(new ArrayList<User>());

    }

    public User(String firstName, String lastName, String email, Household household) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.household = household;
    }
}
