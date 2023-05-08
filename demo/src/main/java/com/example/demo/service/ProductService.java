package com.example.demo.service;

import com.example.demo.Dto.ProductDto;
import com.example.demo.enums.Category;
import com.example.demo.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface ProductService {

    List<ProductDto> getAllProducts();

    Product createProduct(Product product);

    void deteleProduct(Long product_id);

    void updateProduct(Product product,Long id);

    List<Product> findBySex(String sex) ;

    List<Product> findByCategory(Category category);

    List<ProductDto> findBySexBeforeAndCategory(String sex,Category category);

    List<ProductDto> findBySexBeforeAndCategoryAndBrand(String sex,Category category,String brand);

    Optional<Product> findById(long productId);

    List<ProductDto> findAllByNameLike(String name);

    List<ProductDto> findAllBySexAndCategoryAndBrandOrderByPriceAsc(String sex, Category category, String brand);

    List<ProductDto> findAllBySexAndCategoryAndBrandOrderByPriceDesc(String sex, Category category, String brand);

    List<ProductDto> findAllBySexAndCategoryOrderByPriceDesc(String sex, Category category);

    List<ProductDto> findAllBySexAndCategoryOrderByPriceAsc(String sex, Category category);
}
