package com.example.demo.service.impl;

import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.Dto.CartItemDto;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImplementare implements CartService {
    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    @SneakyThrows
    @Override
    public Cart addToCart(AddToCartDto addToCartDto, User user){
        Optional<Product> optionalProduct= productService.findById((long) addToCartDto.getProductId());
        if(!optionalProduct.isPresent())
            throw new Exception("Product not found");
        Product product=optionalProduct.get();
        Cart cart=new Cart(new Date(),product,user, addToCartDto.getQuantity());

        return cartRepository.save(cart);
    }
    @Override
    public CartDto listCartItems(User user){
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItemDtoList=new ArrayList<>();
        double totalCost=0;

        for(Cart cart:cartList){
            CartItemDto c=new CartItemDto(Math.toIntExact(cart.getCart_id()),cart.getQuantity(),cart.getProduct());
            cartItemDtoList.add(c);
            totalCost=totalCost + c.getProduct().getPrice()*c.getQuantity();
        }
        CartDto cartDto=new CartDto();
        cartDto.setCartItems(cartItemDtoList);
        cartDto.setTotalCost(totalCost);
        return cartDto;
    }
    @SneakyThrows
    @Override
    public void deleteCartItem(Long cartItemId,User user)  {
        Optional<Cart> optionalCart=cartRepository.findById((long) cartItemId);
        if(!optionalCart.isPresent()){
            throw new Exception("Cart item is not present");
        }
        Cart cart=optionalCart.get();
        if(cart.getUser()!=user){
            throw new Exception("Cart item does not belong to this user");
        }
        cartRepository.delete(cart);
    }

    @Override
    public void deleteAllCartItems(User user){
        cartRepository.deleteAllByUser(user);
    }
}
