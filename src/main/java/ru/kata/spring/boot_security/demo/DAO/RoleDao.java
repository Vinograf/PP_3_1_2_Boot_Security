package ru.kata.spring.boot_security.demo.DAO;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleById(Long id);
    Role getRoleByName(String name);
    List<Role> getAllRoles();

    void addRole(Role role);
}
