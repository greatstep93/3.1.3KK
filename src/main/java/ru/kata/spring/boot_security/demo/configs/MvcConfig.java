package ru.kata.spring.boot_security.demo.configs;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class MvcConfig {
    private final UserService userService;
    private final RoleService roleService;

    public MvcConfig(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void saveUser() {
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("USER");
        roleService.createRole(role1);
        roleService.createRole(role2);
        User user1 = new User("admin@mail.ru", "Арнольд", "Шварценеггер", (byte) 50,
                "1", role1, role2);
        userService.createUser(user1);
        User user2 = new User("user@mail.ru", "Брюс", "Ли", (byte) 35,
                "2", role2);
        userService.createUser(user2);
    }
}
