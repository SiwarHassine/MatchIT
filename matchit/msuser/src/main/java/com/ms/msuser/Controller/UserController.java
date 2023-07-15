package com.ms.msuser.Controller;

import com.ms.msuser.Entity.RoleEnum;
import com.ms.msuser.Entity.User;
import com.ms.msuser.Services.UserServiceImpl;
import com.ms.msuser.Spring_Security_Jwt.authenticationrequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Autowired
    private UserServiceImpl userService;


    //********************************************************************************************* LOGIN

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> createAuthenticationToken(@RequestBody authenticationrequest authenticationrequest) throws Exception {
        return userService.createAuthenticationToken(authenticationrequest);
    }

    //********************************************************************************************* REGISTRATION
    @PostMapping("/registrate")
    public User adduser(@RequestBody User user) throws Exception {
        return userService.adduser(user);
    }
    @GetMapping("/authority")
    public ResponseEntity<Boolean> checkAuthority(@RequestParam String token, @RequestParam String route) {
       return userService.authoritycheck(token,route);
    }
    @GetMapping("/validate")
    public Boolean adduser(@RequestParam("token") String token) {
        System.out.println("tokken :" + token);
        return userService.validatetoken(token);
    }

    //********************************************************************************************* DELETE USER
    @DeleteMapping("/Delete/{userid}")
    public void deleteUser(@PathVariable("userid") Long UserId) {
        userService.deleteUser(UserId);
    }

    //********************************************************************************************* update
    @PutMapping("/Update")
    public User update(@RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/AllUsers")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/GetById/{userid}")
    public User getbyId(@PathVariable("userid") Long UserId) {
        return userService.getbyID(UserId);
    }

    @PutMapping("/NewPassword")
    public void newpassword(@RequestBody User user) throws Exception {
        userService.NewPassword(user.getId(), user.getPassword());
    }
    
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId) {
        return userService.getbyID(userId);
    }
    
    @GetMapping("/role/{role}")
    public List<User> getUserByRole(@PathVariable RoleEnum role) {
        return userService.getbyRole(role);
    }
    
    
    
}