package com.example.demo.service.impl;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.WishListDto;
import com.example.demo.enums.Category;
import com.example.demo.enums.UserType;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.repository.WishListRepo;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WishListServiceImplTest {
    private WishListServiceImpl underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private WishListRepo wishListRepo;


    @Mock
    private
    ProductService productService;
    @Mock
    private
    UserService userService;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest=new WishListServiceImpl(wishListRepo,productService,userService);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void createWishListTest(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        product.setId(1L);
        user.setUserId(1L);
        WishList wishList=new WishList(user,new Date(),product);
        WishListDto wishListDto=new WishListDto(wishList);
        when(wishListRepo.save(ArgumentMatchers.any(WishList.class))).thenReturn(wishList);
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(productService.findById(1L)).thenReturn(Optional.of(product));
        when(wishListRepo.findAllByUserAndProduct(user,product)).thenReturn(wishList);


        WishListDto  created=underTest.createWishList(user.getEmail(), product.getId());

        verify(wishListRepo).findAllByUserAndProduct(user,product);
    }

    @Test
    void deleteWishListWhenFoundId(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        WishList wishList=new WishList(user,new Date(),product);
        wishList.setWishlistId(1L);


        when(wishListRepo.findByWishlistId(wishList.getWishlistId())).thenReturn(Optional.of(wishList));
        underTest.deleteWishList(wishList.getWishlistId());
        verify(wishListRepo).deleteByWishlistId(wishList.getWishlistId());

    }

    @Test
    void deleteWishListWhenNotFoundId(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        WishList wishList=new WishList(user,new Date(),product);
        wishList.setWishlistId(1L);

        when(wishListRepo.findByWishlistId(anyLong())).thenReturn(Optional.ofNullable(null));
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.deleteWishList(wishList.getWishlistId());

        });
    }


    @Test
    void getWishListWhenNotExists(){
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        when(wishListRepo.findAllByUserOrderByCreatedDateDesc(user)).thenReturn(null);
        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            List<ProductDto> products=underTest.getWishList(user.getEmail());
        });


    }


}
