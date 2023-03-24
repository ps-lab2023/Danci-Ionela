package com.example.demo.model;

import com.example.demo.enums.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable= false,length = 30)
    private String name;
    @Column(nullable= false,length = 30)
    private Category category;
    @Column(length = 200)

    private String brand;
    @Column(nullable= false,length = 2)
    private String sex;
    @Column(nullable= false)
    private Long price;

    private String pictureUrl;

    public Product(String name, Category category, String brand, String sex, Long price, String pictureUrl) {
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.sex = sex;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", brand='" + brand + '\'' +
                ", sex='" + sex + '\'' +
                ", price=" + price +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
