package org.example.BuilderDemo;

public class UserBuilder {

    private static User user;

    public static UserBuilder create() {
        user = new User();
        return new UserBuilder();
    }

    public UserBuilder setFirstName(String firstName) {
        user.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        user.lastName = lastName;
        return this;
    }

    public UserBuilder setAge(int age) {
        user.age = age;
        return this;
    }

    public User build() {
        return user;
    }
}
