package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Column
    private Date createdDate;

    @ManyToOne
    @JoinColumn
    private Product product;

    public WishList(User user, Date createdDate, Product product) {
        this.user = user;
        this.createdDate = createdDate;
        this.product = product;
    }
}
