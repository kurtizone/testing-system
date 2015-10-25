package com.testing.edu.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoDTO {
    private String username;
    private List<String> roles;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean enable;

    public UserInfoDTO(String username, List<String> roles, String firstName, String lastName, String phone, boolean enable) {
        this.username = username;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.enable = enable;
    }
}
