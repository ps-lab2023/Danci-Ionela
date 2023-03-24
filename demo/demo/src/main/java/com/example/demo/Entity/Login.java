package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data



public class Login {
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    private Long login_id;
    @Column(nullable= false,length = 30)
    private String login_role_id;
    @Column(nullable= false,length = 30)
    private String login_username;
    @Column(nullable= false,length = 30)
    private String user_password;

}
