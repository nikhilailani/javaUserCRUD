package models;

import java.util.Scanner;

public class User {
    private String userName, firstName, lastName, DOB, role, password;

    public User(){
        System.out.println("User created!");
    }

    public User(String userName, String firstName, String lastName, String DOB, String role, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.role = role;
        this.password = password;
    }

    public boolean verifyUser (String password) {
        return this.password == password;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getRole() {
        return this.role;
    }

    public String getDOB() {
        return this.DOB;
    }
}
