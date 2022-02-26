package com.example.amtvregistrationdemo;

import java.util.ArrayList;

public class Household {
    public ArrayList<User> members;

    public Household() {}

    public Household(ArrayList<User> users) {
        members = users;
    }

}
