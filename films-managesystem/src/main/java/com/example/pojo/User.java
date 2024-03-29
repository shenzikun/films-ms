package com.example.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String role;
    private String phone;
    private String email;
    private String address;
}
