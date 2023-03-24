package com.example.demo;

import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.enums.Category;
import com.example.demo.enums.UserType;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.repository.*;
import com.example.demo.service.impl.CartServiceImplementare;
import com.example.demo.service.impl.ProductServiceImplementare;
import com.example.demo.service.impl.UserServiceImplementare;
import com.example.demo.service.impl.WishListServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.demo.enums.UserType.ADMIN;
import static com.example.demo.enums.UserType.BUYER;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, ProductRepository productRepository, WishListRepo wishListRepo, CartRepository cartRepository, ActivityLogRepository activityLogRepository) {
        return args -> {
            UserServiceImplementare userServiceImplementare=new UserServiceImplementare(userRepository,activityLogRepository);


            User user1=new User("user1@gmail.com","user1",BUYER);
            User user2=new User("user2@gmail.com","user2",BUYER);

            User admin=new User("admin@gmail.com","adminn",ADMIN);

            user1=userServiceImplementare.createUser(user1);
            user2=userServiceImplementare.createUser(user2);
            admin=userServiceImplementare.createUser(admin);



            userServiceImplementare.register(user1);
            userServiceImplementare.register(admin);

            User testLoginUser1=userServiceImplementare.loginBuyer(user1);
            System.out.println("Login user1 "+testLoginUser1);
            try {
                User testLoginUser2=userServiceImplementare.loginBuyer(user2);
            }catch (Exception exception){
                System.out.println("User 2 nu este inregistrat");
            }
            User testLoginAdmin=userServiceImplementare.loginBuyer(admin);
            System.out.println("Login admin  "+testLoginAdmin);

            User testUserLogout=userServiceImplementare.logOut(user1);
            System.out.println("Log out   " + testLoginAdmin);

            Product product1=new Product("p1", Category.Boots,"Zara","F", 200L,"zaraBoots.url");
            Product product2=new Product("p2", Category.Trainers,"Adidas","F", 500L,"adidaTrainers.url");
            Product product3=new Product("p3", Category.Trainers,"Nike","M",600L,"nikeTrainers.url");
            Product product4=new Product("p4", Category.Boots,"Zara","M", 300L,"dkc.url");

            ProductServiceImplementare productServiceImplementare=new ProductServiceImplementare(productRepository);

            product1=productServiceImplementare.createProduct(product1);
            product2=productServiceImplementare.createProduct(product2);
            product3=productServiceImplementare.createProduct(product3);
            product4=productServiceImplementare.createProduct(product4);

            WishListServiceImpl wishListService =new WishListServiceImpl(wishListRepo,productServiceImplementare);
            WishList wishList=new WishList(user1,new Date(),product1);
            wishList=wishListService.createWishList(wishList);
            WishList wishList2=new WishList(user1,new Date(),product2);
            wishList2=wishListService.createWishList(wishList2);

            List<Product> pr=wishListService.getWishList(user1);
            System.out.println("Product List for User " + user1);
            for (Product p: pr){
                System.out.println(p);
            }

            CartServiceImplementare cartServiceImplementare=new CartServiceImplementare(productServiceImplementare,cartRepository);
            AddToCartDto addToCartDto=new AddToCartDto(1L,product1.getId(),3);
            AddToCartDto addToCartDto2=new AddToCartDto(2L,product2.getId(),2);

            Cart cart1=cartServiceImplementare.addToCart(addToCartDto,user1);
            Cart cart2=cartServiceImplementare.addToCart(addToCartDto2,user1);

            CartDto cartDto=cartServiceImplementare.listCartItems(user1);
            System.out.println(cartDto);




        };
    }



}
