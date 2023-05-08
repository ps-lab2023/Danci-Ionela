package com.example.demo.service;

import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.WishListDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface WishListService {

    @SneakyThrows
    WishListDto createWishList(String email, Long productId);

    @SneakyThrows
    List<ProductDto> getWishList(String email);

    Optional<WishList> findById(Long id);

    @SneakyThrows
    void deleteWishList(Long id);

    @SneakyThrows
    void delete_from_wishlist(String email, Long productId);
}
