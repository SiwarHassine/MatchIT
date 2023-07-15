package com.c2s.dsif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c2s.dsif.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
