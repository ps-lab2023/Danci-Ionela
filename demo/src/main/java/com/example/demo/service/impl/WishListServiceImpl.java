package com.example.demo.service.impl;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.repository.WishListRepo;
import com.example.demo.service.ProductService;
import com.example.demo.service.WishListService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService {
    @Autowired
    WishListRepo wishListRepo;

    @Autowired
    ProductService productService;
    @Override
    public WishList createWishList(WishList wishList){
        return wishListRepo.save(wishList);
    }

    @Override
    public List<Product> getWishList(User user){
        final List<WishList> wishLists =wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<Product> productList=new ArrayList<>();
        for(WishList w:wishLists){
            productList.add(w.getProduct());
        }
        return productList;
    }

    @Override
    public Optional<WishList> findById(Long id){
        return wishListRepo.findById(id);
    }

    @SneakyThrows
    @Override
    public void deleteWishList(Long id){
        wishListRepo.findByWishlistId(id).orElseThrow(()->new Exception("Id not found"));
        wishListRepo.deleteByWishlistId(id);
    }


}
