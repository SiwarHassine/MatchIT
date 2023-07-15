package com.ms.msuser.Services;

import com.ms.msuser.Entity.RoleEnum;
import com.ms.msuser.Entity.User;
import com.ms.msuser.Interfaces.IuserService;
import com.ms.msuser.Repository.UserRepo;
import com.ms.msuser.RouteValidator.CandidatRoute;
import com.ms.msuser.RouteValidator.RecruteurRoute;
import com.ms.msuser.Spring_Security_Jwt.JwtUtil;
import com.ms.msuser.Spring_Security_Jwt.authenticationrequest;
import com.ms.msuser.Spring_Security_Jwt.authenticationresponse;
import com.ms.msuser.Spring_Security_Jwt.myUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IuserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private myUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> createAuthenticationToken(authenticationrequest authenticationrequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationrequest.getEmail(), authenticationrequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username and password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationrequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Map map=new HashMap();
        map.put("user",userRepo.findUserByEmail(authenticationrequest.getEmail()));
        map.put("tokken",new authenticationresponse(jwt));
        //  return ResponseEntity.ok(new authenticationresponse(jwt));ResponseEntity<?>
        return map;

    }

    @Override
    public User adduser(User user) throws Exception {
        if (!userRepo.findUserByEmail(user.getEmail()).isPresent()) {
            String genpwd = generateCommonLangPassword();
            System.out.println("password : " + genpwd);
            user.setPassword(passwordEncoder.encode(genpwd));
            user.setActive(0L);

            try {
            	//sendEmail(user, genpwd);
                System.out.println("user : " + user);
                User result = userRepo.saveAndFlush(user);

                return result;
            } catch (Exception e) {
                System.out.println("invalid email " + user.getEmail());
                throw new ResourceNotFoundException("invalid email");
            }
        } else {
            System.out.println("email exist: " + user.getEmail());
            throw new ResourceNotFoundException("email exist");
        }
    }

    @Override
    public User update(User user) {
        User u = userRepo.findById(user.getId()).get();
        System.out.println(u);
    	u.setFirstname(user.getFirstname());
    	u.setName(user.getName());
    	u.setDatenaisance(user.getDatenaisance());
    	u.setPhone(user.getPhone());
    	u.setEmail(user.getEmail());
    	
    	
    	//user.setGenre(u.getGenre());
       //user.setCandidattype(u.getCandidattype());
        return userRepo.save(u);
    }
    
    @Override
    public void deleteUser(Long UserId) {
        userRepo.deleteById(UserId);
    }

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public User getbyID(Long UserId) {
        return userRepo.findById(UserId).get();
    }
    
    @Override
    public List<User> getbyRole(RoleEnum role) {
        return userRepo.findUserByRoles(role);
    }


    @Override
    public void NewPassword(Long UserId, String password) throws Exception {
        User u = userRepo.findById(UserId).get();
        u.setPassword(passwordEncoder.encode(password));
        if (u.getActive() == 0) {            
            u.setActive(1L);
            //sendEmail(u, password);
            userRepo.save(u);
        } else {
        	userRepo.save(u);
            System.out.println("account active");
        }

    }

    @Override
    public Boolean validatetoken(String tokken) {
        String name = this.jwtTokenUtil.extractUsername(tokken);
        return this.jwtTokenUtil.validateToken(tokken, userDetailsService
                .loadUserByUsername(name));
    }

    @Override
    public ResponseEntity<Boolean> authoritycheck(String token, String route) {
        Boolean existuser= validatetoken(token);
        List<String> roles = null;
        if (existuser){
            roles = jwtTokenUtil.extractClaim(token, claims -> {
                Object rolesObject = claims.get("roles");
                if (rolesObject instanceof List) {
                    return (List<String>) rolesObject;
                }
                return null;
            });
        }

        System.out.println("role from token is :"+ roles.get(0));
        if (existuser && hasAuthority(roles, route)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    private boolean hasAuthority(List<String> roles, String route) {
        System.out.println("route : "+ route);
        System.out.println("role user : "+ roles.get(0));
        boolean isRouteFound=false;
        if (roles.get(0).equals("RECRUTEUR")){
            isRouteFound = Arrays.stream(RecruteurRoute.values())
                    .map(RecruteurRoute::getRoute)
                    .anyMatch(a -> route.contains(a));
            System.out.println("isRouteFound For RECRUTEUR: "+ isRouteFound);
            return isRouteFound;
        }else if (roles.get(0).equals("CANDIDAT")){
            isRouteFound = Arrays.stream(CandidatRoute.values())
                    .map(CandidatRoute::getRoute)
                    .anyMatch(a -> route.contains(a));
            System.out.println("isRouteFound For CANDIDAT: "+ isRouteFound);
            return isRouteFound;
        }else if (roles.get(0).equals("ADMIN")){
            return true;
        }
        return isRouteFound;
    }
    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 40, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public void sendEmail(User user, String password) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        final String[] emailbody = new String[2];
        emailbody[0] = user.getFirstname() + " " + user.getName();
        String htmlMsg = "<body style='border:1px solid black;border-radius:5px;padding:10px;'>"
                + "- " + emailbody[0] + " Your onetime password for registration is : <h3 style='color:red;'>" + password
                + "</h3> Please use this password to complete your new user registration on your first log in." +
                "</body>";
        String htmlMsg2 = "<body style='border:1px solid black;border-radius:5px;padding:10px;'>"
                + "- " + emailbody[0] + " Your new password is : <h3 style='color:red;'>" + password
                + "</h3></body>";

        try {
            System.out.println("sending email");
            helper.setTo(user.getEmail());
            if (user.getActive() == 0) {
                message.setContent(htmlMsg, "text/html");
                helper.setSubject("Welcome to Sofrecom recruitment ");
            } else {
                message.setContent(htmlMsg2, "text/html");
                helper.setSubject("new password ");
            }
            javaMailSender.send(message);
            System.out.println("email sent");
        } catch (Exception e) {
            System.out.println("failed to send email");
            throw new Exception("invalid mail");
        }
    }
    
    
}
