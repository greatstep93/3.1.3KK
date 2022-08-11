package ru.kata.spring.boot_security.demo.repository.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUser();
    User getUserByUsername(String username);
    void createUser(User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    void updateUser(User user);
}