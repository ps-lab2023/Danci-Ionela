package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface WishListService {
    WishList createWishList(WishList wishList);

    List<Product> getWishList(User user);

    Optional<WishList> findById(Long id);

    @SneakyThrows
    void deleteWishList(Long id);
}
