package com.example.demo.service;


import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public interface CartService {
    Cart addToCart(AddToCartDto addToCartDto, User user);

    CartDto listCartItems(User user);

    void deleteCartItem(Long cartItemId, User user);

    void deleteAllCartItems(User user);
}
