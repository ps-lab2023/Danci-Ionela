package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItems;
    private double totalCost;

    public CartDto() {
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "cartItems=" + cartItems +
                ", totalCost=" + totalCost +
                '}';
    }
}
