package com.capstone.models;

import lombok.Data;

@Data
public class User {

    private int id;
    private String name;
    private String role_id;
    private String role;
    private String email;
    private String password;
    private double salary;
}
