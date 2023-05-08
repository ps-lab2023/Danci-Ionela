package com.example.demo.Dto;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import jakarta.persistence.*;

import java.util.Date;

public class WishListDto {
    private Long wishlistId;
    private String userEmail;
    private Date createdDate;
    private Long productId;

    public WishListDto(Long wishlistId, String userEmail, Date createdDate, Long productId) {
        this.wishlistId = wishlistId;
        this.userEmail=userEmail;
        this.createdDate = createdDate;
        this.productId = productId;
    }

    public WishListDto(WishList wishList) {
        this.wishlistId = wishList.getWishlistId();
        this.userEmail = wishList.getUser().getEmail();
        this.createdDate = wishList.getCreatedDate();
        this.productId = wishList.getProduct().getId();
    }

    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
