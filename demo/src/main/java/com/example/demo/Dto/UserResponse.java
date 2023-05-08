package com.example.demo.Dto;

import com.example.demo.enums.UserType;
import com.example.demo.model.User;

public class UserResponse {

    private String email;
    private Boolean loged;
    private UserType role;

    public UserResponse(User user){

        this.email= user.getEmail();;
        this.role=user.getRole();
        this.loged=user.isLoged();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    public Boolean getLoged() {
        return loged;
    }

    public void setLoged(Boolean loged) {
        this.loged = loged;
    }
}
