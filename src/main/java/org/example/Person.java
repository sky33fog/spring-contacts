package org.example;

public class Person {

    public String fullName;

    public String phoneNumber;

    public String email;

    public Person(String fullName, String phoneNumber, String email) {
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber.trim();
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return fullName + " | " + phoneNumber + " | " + email;
    }
}
