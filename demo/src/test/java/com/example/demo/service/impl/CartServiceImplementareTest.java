package com.example.demo.service.impl;

import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.Dto.CartItemDto;
import com.example.demo.enums.Category;
import com.example.demo.enums.UserType;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityLogRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.WishListRepo;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CartServiceImplementareTest {
    private CartServiceImplementare underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CartRepository cartRepository;

    @Mock
    private
    ProductService productService;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest=new CartServiceImplementare(productService,cartRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void addToCartWhenProductExists(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        product.setId(3L);
        AddToCartDto addToCartDto=new AddToCartDto(1L,product.getId(),3);
        when(productService.findById(product.getId())).thenReturn(Optional.of(product));
        Cart cartE=new Cart(new Date(),product,newUser,addToCartDto.getQuantity());
        when(cartRepository.save(ArgumentMatchers.any(Cart.class))).thenReturn(cartE);


        Cart created=underTest.addToCart(addToCartDto,newUser);
        assert(created.getUser().equals(newUser));
        assert(created.getProduct().equals(product));
    }

    @Test
    void addToCartWhenProductDoesntExists(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        product.setId(3L);
        AddToCartDto addToCartDto=new AddToCartDto(1L,product.getId(),3);

        when(productService.findById(product.getId())).thenReturn(Optional.ofNullable(null));

        Exception exception=assertThrows(Exception.class, ()-> {
            Cart created=underTest.addToCart(addToCartDto,newUser);
        });

    }


    @Test
    void listCartItems(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        product.setId(3L);
        AddToCartDto addToCartDto=new AddToCartDto(1L,product.getId(),3);
        Cart cart=new Cart(new Date(),product,newUser,addToCartDto.getQuantity());
        cart.setCart_id(1L);
        List<Cart> cartList=new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findAllByUserOrderByCreatedDateDesc(newUser)).thenReturn(cartList);
        CartItemDto c=new CartItemDto(Math.toIntExact(cart.getCart_id()),cart.getQuantity(),cart.getProduct());
        List<CartItemDto> cartItemDtoList=new ArrayList<>();
        cartItemDtoList.add(c);
        CartDto cartDto=new CartDto(cartItemDtoList,600L);
        CartDto created=underTest.listCartItems(newUser);
        assert(created.getTotalCost()==(cartDto.getTotalCost()));
    }

    @Test
    void deleteCartItem(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        product.setId(3L);
        AddToCartDto addToCartDto=new AddToCartDto(1L,product.getId(),3);
        Cart cart=new Cart(new Date(),product,newUser,addToCartDto.getQuantity());
        cart.setCart_id(1L);
        when(cartRepository.findById(cart.getCart_id())).thenReturn(Optional.of(cart));
        underTest.deleteCartItem(cart.getCart_id(),newUser);
        verify(cartRepository).delete(cart);
    }
    @Test
    void deleteCartItemWhenInvalidCart(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        product.setId(3L);
        AddToCartDto addToCartDto=new AddToCartDto(1L,product.getId(),3);
        Cart cart=new Cart(new Date(),product,newUser,addToCartDto.getQuantity());
        cart.setCart_id(1L);


        when(cartRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Exception exception=assertThrows(Exception.class , () -> {
            underTest.deleteCartItem(cart.getCart_id(),newUser);
        });
    }

    @Test
    void deleteCartItemWhenInvalidCartForThisUser(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        User newUser2=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser2.setUserId(3L);

        product.setId(3L);
        AddToCartDto addToCartDto=new AddToCartDto(1L,product.getId(),3);
        Cart cart=new Cart(new Date(),product,newUser,addToCartDto.getQuantity());
        cart.setCart_id(1L);


        when(cartRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        Exception exception=assertThrows(Exception.class , () -> {
            underTest.deleteCartItem(cart.getCart_id(),newUser2);
        });
    }

    @Test
    void deleAllCartItemsTest(){
        User newUser=new User("User2@gmail.com","user2", UserType.BUYER);
        newUser.setUserId(1L);
        underTest.deleteAllCartItems(newUser);
        verify(cartRepository).deleteAllByUser(newUser);
    }

}