package comp3350.group6.homiez.persistence;

import java.util.List;

import comp3350.group6.homiez.objects.User;

public interface DataAccess {
    void open(String string);

    void close();

    String getAllUsers(List<User> userList);

    String getUserById(User user, int userId);

    String getUsersByAge(List<User> userList, int minAge, int maxAge);

    String insertUser(User user);

    String updateUser(User user);

    String deleteUser();
}
