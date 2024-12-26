package org.example.BuilderDemo;

public class UserDemo {

    public static void main(String[] args) {
        User user = UserBuilder.create().setFirstName("Иван").build();
        System.out.println(user);

        user = UserBuilder.create().setAge(15).setLastName("Иванов").build();
        System.out.println(user);
    }
}
