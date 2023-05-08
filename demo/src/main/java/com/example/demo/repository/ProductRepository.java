package com.example.demo.repository;

import com.example.demo.enums.Category;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    Product findByBrand(String brand);
    List<Product> findBySex(String sex);
    List<Product> findByCategory(Category category);

    List<Product> findAllBySexAndCategory(String sex,Category category);

    List<Product> findAllBySexAndCategoryOrderByPriceAsc(String sex,Category category);
    List<Product> findAllBySexAndCategoryOrderByPriceDesc(String sex,Category category);
    List<Product> findAllBySexAndCategoryAndBrand(String sex,Category category,String brand);

    List<Product> findAllBySexAndCategoryAndBrandOrderByPriceAsc(String sex,Category category,String brand);
    List<Product> findAllBySexAndCategoryAndBrandOrderByPriceDesc(String sex,Category category,String brand);

    List<Product> findBySexBeforeAndCategoryAndPrice(String sex,Category category,Long pret);
    void deleteById(Long id);
    Optional<Product> findById(Long id);

    Product findByName(String name);
    //List<Product> findAllByNameLike(String name);

    List<Product> findAllByNameLike(String name);



//    List<Product> findAllBySexBeforeAndCategoryOrderByPrice(String sex,Category category,Double price);
//    List<Product> findAllBySexBeforeAndCategoryAndBrandOrderByPrice(String sex,Category category,String brand,Double price);

}

