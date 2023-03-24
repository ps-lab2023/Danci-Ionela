package com.example.demo.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddToCartDto {
    private Long id;
    private Long productId;
    private int quantity;

    public AddToCartDto() {
    }

}
