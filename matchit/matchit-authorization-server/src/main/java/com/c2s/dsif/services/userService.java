package com.c2s.dsif.services;

import java.util.List;


import com.c2s.dsif.DTO.UserDto;
import com.c2s.dsif.entities.User;

public interface userService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
    
}