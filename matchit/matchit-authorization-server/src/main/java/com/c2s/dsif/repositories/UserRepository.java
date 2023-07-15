package com.c2s.dsif.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.c2s.dsif.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

	User findByUsername(String username);
    

}