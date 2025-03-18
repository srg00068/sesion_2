package com.example.sesion2;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String username;
    private String email;
    private String password;
    private String role;

    public Usuario(){
        username="";
        email="";
        password="";
        role="";
    }

    public Usuario(String username, String email, String password){
        this.username = username;
        this.email=email;
        this.password=password;
        this.role="normal";
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }


    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
}

