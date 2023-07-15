package com.c2s.dsif.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c2s.dsif.DTO.UserDto;
import com.c2s.dsif.entities.User;
import com.c2s.dsif.services.UserDetailsImpl;
import com.c2s.dsif.services.userService;
import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;


@Controller
public class authController {
	
	private userService userService;
	private final JwtEncoder encoder;

    public authController(userService userService, JwtEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    // handler method to handle user registration form request
   // @GetMapping("/register")
    //public String showRegistrationForm(Model model){
        // create model object to store form data
      //  UserDto user = new UserDto();
       // model.addAttribute("user", user);
       // return "register";
  //  }

    // handler method to handle user registration form submit request
	@PostMapping("/register/save")
	@ResponseBody
	public Map<String, Object> registration(@Valid @RequestBody UserDto userDto, BindingResult result){
	    Map<String, Object> response = new HashMap<>();
	
	    User existingUser = userService.findUserByEmail(userDto.getEmail());
	
	    if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
	        response.put("success", false);
	        response.put("message", "There is already an account registered with the same email");
	        return response;
	    }
	
	    if(result.hasErrors()){
	        response.put("success", false);
	        response.put("message", "Invalid form data");
	        response.put("erreur", result);
	        return response;
	    }
	
	    userService.saveUser(userDto);
	
	    response.put("success", true);
	    response.put("message", "User registration successful");
	    return response;
	}
	    
    // handler method to handle list of users
    @GetMapping("/users")
    @ResponseBody
    public List<UserDto> getUsers(){
        List<UserDto> users = userService.findAllUsers();
        return users;
    }
    	



    @PostMapping("/token")
    public String token(Authentication authentication) {
        return this.generateToken(authentication);
    }

    public String generateToken(Authentication authentication) {
    	UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        Instant now = Instant.now();
        
        String scope = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(authority -> authority.startsWith("ROLE"))
                .collect(Collectors.joining(" "));
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(userPrincipal.getUsername())
                .claim("scope", scope)
                .build();
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }


    

}
