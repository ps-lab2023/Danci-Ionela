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


public class User {

    @Id
    private Long user_id;
    @Column(nullable= false,length = 30)
    private String user_name;
    @Column(nullable= false,length = 30)
    private String user_mobile;
    @Column(nullable= false,length = 30)
    private String user_email;
    @Column(nullable= false,length = 30)
    private String user_address;
}
