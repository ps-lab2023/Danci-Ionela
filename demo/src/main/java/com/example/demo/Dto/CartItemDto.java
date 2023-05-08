package com.example.demo.Dto;

import com.example.demo.model.Product;

public class CartItemDto {
    private int id;
    private int quantity;
    private Product product;

    public CartItemDto() {
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }

    public CartItemDto(int id, int quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String prettyPrint() {
        String s="\to "+product.getName() +"\tPrice=  "+product.getPrice()+"$\n\n";
        return s;
    }
}
