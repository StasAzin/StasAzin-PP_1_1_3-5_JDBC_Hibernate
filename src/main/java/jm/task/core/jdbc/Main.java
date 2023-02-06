package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("Adam", "God", (byte) 100);
        userService.saveUser("Eva", "God", (byte) 100);
        userService.saveUser("Cat", "NaN", (byte) 2);
        userService.saveUser("Dog", "NaN", (byte) 3);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
