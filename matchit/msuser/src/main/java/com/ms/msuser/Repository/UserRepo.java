package com.ms.msuser.Repository;

import com.ms.msuser.Entity.RoleEnum;
import com.ms.msuser.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    
    List<User> findUserByGenre(String genre);
    
    List<User> findUserByDatenaisance(Date date);
    
    List<User> findUserByRoles(RoleEnum roles);
        
   
 
}
