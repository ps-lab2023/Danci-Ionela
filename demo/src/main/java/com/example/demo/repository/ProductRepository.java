package com.example.demo.repository;

import com.example.demo.enums.Category;
import com.example.demo.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    Product findByBrand(String brand);
    List<Product> findBySex(String sex);
    List<Product> findByCategory(Category category);
    List<Product> findBySexBeforeAndCategory(String sex,Category category);
    List<Product> findBySexBeforeAndCategoryAndBrand(String sex,Category category,String brand);
    List<Product> findBySexBeforeAndCategoryAndPrice(String sex,Category category,Long pret);
    void deleteById(Long id);
    Optional<Product> findById(Long id);

    Product findByName(String name);

    /*List<Product> findBySexBeforeAndCategoryAndBrandOrderByPrice(String sex, Category category, String brand,Long price);

    //List<Product> findBySexBeforeAndCategoryOrderByPrice(String sex , Category category , Long price);

    List<Product> findBySexOrderByPrice(String sex,Long price);

    List<Product> findByCategoryOrderByPrice(Category category,Long price);*/

}

