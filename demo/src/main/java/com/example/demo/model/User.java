package com.example.demo.model;

import com.example.demo.enums.UserType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(nullable= false,length = 30)
    private String email;
    @Column(nullable= false,length = 30)
    private String password;

    @Column(nullable= false)
    private UserType role;

    public User(String email, String password, UserType role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }



}
