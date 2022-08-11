package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(ModelMap model, Principal principal) {
        model.addAttribute("users", userService.getAllUser());
        User user = new User();
        List<Role> roles = roleService.getAllRole();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        model.addAttribute("ourUser", userService.loadUserByUsername(principal.getName()));
        return "admin";
    }

    @GetMapping(value = "/userPage")
    public String getUserPage(ModelMap model, Principal principal) {
        model.addAttribute("ourUser", userService.loadUserByUsername(principal.getName()));
        return "userPage";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "login";
    }

    @GetMapping(value = "users/add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(name = "rolesSelected",
            defaultValue = "0") Long[] rolesId) {
        List<Long> listRolesId = Arrays.asList(rolesId);
        Set<Role> roles = new HashSet<>(roleService.getRolesByIds(listRolesId));
        user.setRoles(roles);
        userService.createUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "users/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "users/update")
    public String updateUser(User user, @RequestParam(name = "rolesSelected", defaultValue = "0") Long[] rolesId) {
        List<Long> listRolesId = Arrays.asList(rolesId);
        Set<Role> roles = new HashSet<>(roleService.getRolesByIds(listRolesId));
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
