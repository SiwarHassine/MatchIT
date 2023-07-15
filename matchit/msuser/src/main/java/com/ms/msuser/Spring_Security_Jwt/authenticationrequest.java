package com.ms.msuser.Spring_Security_Jwt;


public class authenticationrequest {
    private String email;
    private String password;

    public authenticationrequest() {
    }

    public authenticationrequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
