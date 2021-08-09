package com.example.core.example.reflection.domain;

import lombok.Data;

@Data
public class User {

    public String username;
    private String password;

    public String hello(){
        return "hello world!";
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
