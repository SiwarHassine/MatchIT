package com.ms.msuser.Spring_Security_Jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ms.msuser.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MyUserDetails implements UserDetails {
    private String userName;
    @JsonIgnore
    private String password;
    private final GrantedAuthority authorities;

    public MyUserDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new SimpleGrantedAuthority(user.getRoles().name());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(this.authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
