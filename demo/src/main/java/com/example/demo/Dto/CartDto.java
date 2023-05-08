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

    public String prettyPrint(){
        String s="\n\n\nYour products:\n\n";
        for(CartItemDto cartItemDto:cartItems){
            s+=cartItemDto.prettyPrint();
        }
        s+="\nTotal Cost= "+totalCost + "$\n\n\n";
        return s;
    }
}
