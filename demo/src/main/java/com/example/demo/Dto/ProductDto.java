package com.example.demo.Dto;

import com.example.demo.enums.Category;
import com.example.demo.model.Product;

public class ProductDto  {
    private Long id;
    private String name;
    private Category category;
    private String brand;
    private String sex;
    private Long price;
    private String pictureUrl;

    public ProductDto(Long id, String name, Category category, String brand, String sex, Long price, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.sex = sex;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public ProductDto(Product product){
        this.id=product.getId();
        this.name=product.getName();
        this.category=product.getCategory();
        this.brand=product.getBrand();
        this.sex=product.getSex();
        this.price=product.getPrice();
        this.pictureUrl=product.getPictureUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
