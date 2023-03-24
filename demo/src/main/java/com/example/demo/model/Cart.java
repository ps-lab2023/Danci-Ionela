package com.example.demo.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cart_id;

    @Column(name = "created_date")
    private Date createdDate;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;

    public Cart(Date createdDate, Product product, User user, int quantity) {
        this.createdDate = createdDate;
        this.product = product;
        this.user = user;
        this.quantity = quantity;
    }

}
