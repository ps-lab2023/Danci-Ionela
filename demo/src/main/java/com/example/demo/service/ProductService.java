package com.example.demo.service;

import com.example.demo.enums.Category;
import com.example.demo.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProductService {


    List<Product> getAllProducts();
    Product createProduct(Product product);
    void deteleProduct(Long product_id);
    void updateProduct(Product product,Long id);
    List<Product> findBySex(String sex) ;
    List<Product> findByCategory(Category category);
    List<Product> findBySexBeforeAndCategory(String sex,Category category);
    List<Product> findBySexBeforeAndCategoryAndBrand(String sex,Category category,String brand);
    List<Product> findBySexBeforeAndCategoryAndPrice(String sex,Category category,Long pret);

    Optional<Product> findById(long productId);

    /*List<Product> findBySexBeforeAndCategoryAndBrandOrderByPrice(String sex, Category category, String brand,Long price);

    //List<Product> findBySexBeforeAndCategoryOrderByPrice(String sex, Category category, Long price);

    List<Product> findBySexOrderByPrice(String sex, Long price);

    List<Product> findByCategoryOrderByPrice( Category category, Long price);*/
}
