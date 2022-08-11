package ru.kata.spring.boot_security.demo.repository.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    void createRole(Role role);
    List<Role> getAllRole();
    Role getRoleById(Long id);
    List<Role> getRolesByIds(List<Long> ids);
}
