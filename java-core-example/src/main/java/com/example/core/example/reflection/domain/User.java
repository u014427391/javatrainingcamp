package com.example.core.example.reflection.domain;

import lombok.Data;

@Data
public class User {

    public String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
