package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAllUser();
    void createUser(User user);
    void deleteUser(Long id);
    User getUserById(Long id);
    void updateUser(User user);

    void setUserRolesByIds(User user, Long[] rolesId);
}
