package com.example.demo.controller;

import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.model.Cart;
import com.example.demo.model.User;
import com.example.demo.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cart")
@CrossOrigin("http://localhost:3000")
public class CartController {
    @Autowired
    private CartService cartService;

    @DeleteMapping("/delete_product")
    public void deleteCartItem(@RequestParam String email,@RequestParam Long productId){
        cartService.deleteCartItem(email,productId);
    }

    @DeleteMapping("/delete_all_cart_items")
    public void deleteCart(@RequestParam String userEmail){
        cartService.deleteAllCartItems(userEmail);
    }

    @PostMapping("/add_to_cart")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Cart> addToCart(@Valid @RequestBody AddToCartDto addToCartDto, @RequestParam String email) {
        Cart createdCart = cartService.addToCart(addToCartDto,email);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdCart)
                .toUri();

        return ResponseEntity.created(uri).body(createdCart);
    }

    @CrossOrigin("http://localhost:3000")
    @GetMapping("list_cart_items")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartDto> listCartItems(@RequestParam String email) {
        return ResponseEntity.ok().body(cartService.listCartItems(email));
    }

    @PostMapping("/send_email")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(@RequestParam String email) {
        cartService.sendEmail(email);
    }


}
