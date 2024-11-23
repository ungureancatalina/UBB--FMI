package com.example.lab6fx.domain;

public class LoggedIn {
    private static String firstName;
    private static String lastName;

    public static String getFirstName() {
        return firstName;
    }

    public static void setFirstName(String firstName) {
        LoggedIn.firstName = firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static void setLastName(String lastName) {
        LoggedIn.lastName = lastName;
    }
}
