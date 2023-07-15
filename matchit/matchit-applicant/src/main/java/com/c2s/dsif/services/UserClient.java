package com.c2s.dsif.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.c2s.dsif.Dto.userDto;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

	@GetMapping("/user/GetById/{userid}")
    public userDto getbyId(@PathVariable("userid") Long UserId) ;
    
    @GetMapping("/user/AllUsers")
    public List<userDto> getAll();
}