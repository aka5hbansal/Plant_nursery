package com.example.plantnursery.DTOs;


import com.example.plantnursery.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String username;
    private String password;

    public UserDTO() {
    }

    public UserDTO(User user) {

        this.username = user.getUsername();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}



