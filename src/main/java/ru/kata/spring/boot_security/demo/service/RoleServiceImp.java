package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class RoleServiceImp implements RoleService{
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Transactional
    @Override
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }
    @Transactional
    @Override
    public Set<Role> findByIdRoles(List<Long> roles) {
        return new HashSet<>(roleRepository.findAllById(roles));
    }
//    @Transactional
//    @Override
//    @PostConstruct
//    public void addDefaultRole() {
//        roleRepository.save(new Role(1L,"ROLE_USER"));
//        roleRepository.save(new Role(2L,"ROLE_ADMIN"));
//    }

    public List<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
