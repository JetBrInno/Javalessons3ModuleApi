package org.example.BuilderDemo;

public class User {

    String firstName;

    String lastName;

    int age;

    String city;

    String email;

    String phone;

    @Override
    public String toString() {
        return "User{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", age=" + age +
            ", city='" + city + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            '}';
    }
}
