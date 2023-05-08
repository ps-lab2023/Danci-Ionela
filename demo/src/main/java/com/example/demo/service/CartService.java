package com.example.demo.service;


import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public interface CartService {
    Cart addToCart(AddToCartDto addToCartDto, String email);

    CartDto listCartItems(String email);

    void sendEmail(String email);

    void deleteCartItem(String email, Long productId);

    void deleteAllCartItems(String email);
}
