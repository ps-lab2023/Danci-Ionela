package com.example.demo.service.impl;

import com.example.demo.enums.Category;
import com.example.demo.enums.UserType;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.repository.ActivityLogRepository;
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

import static java.util.Objects.isNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private ActivityLogRepository activityLogRepository;

    @Mock
    private
    ProductService productService;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest=new WishListServiceImpl(wishListRepo,productService);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void createWishListTest(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        WishList wishList=new WishList(user,new Date(),product);
        when(wishListRepo.save(ArgumentMatchers.any(WishList.class))).thenReturn(wishList);

        WishList created=underTest.createWishList(wishList);
        assertThat(created.getCreatedDate()).isSameAs(wishList.getCreatedDate());
        assertThat(created.getUser()).isSameAs(wishList.getUser());
        assertThat(created.getProduct()).isSameAs(wishList.getProduct());

        verify(wishListRepo).save(wishList);
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
    void getWishListWhenExists(){
        Product product=new Product("p1", Category.Boots,"Zara","F", 200L,"dkc.url");
        Product product2=new Product("p2", Category.Boots,"Zara","F", 200L,"dkc.url");
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        WishList wishList=new WishList(user,new Date(),product);
        WishList wishList2=new WishList(user,new Date(),product2);
        wishList.setWishlistId(1L);
        wishList.setWishlistId(2L);
        List<Product> productList=new ArrayList<>();
        productList.add(wishList.getProduct());
        productList.add(wishList2.getProduct());
        List<WishList> wishListList=new ArrayList<>();
        wishListList.add(wishList);
        wishListList.add(wishList2);
        when(wishListRepo.findAllByUserOrderByCreatedDateDesc(user)).thenReturn(wishListList);
        List<Product> products=underTest.getWishList(user);
        assert(products.containsAll(productList));
    }

    @Test
    void getWishListWhenNotExists(){
        User user=new User("User1@gmail.com","user1", UserType.BUYER);
        when(wishListRepo.findAllByUserOrderByCreatedDateDesc(user)).thenReturn(null);
        Exception exception = assertThrows(NullPointerException.class, () -> {
            List<Product> products=underTest.getWishList(user);
        });


    }


}
