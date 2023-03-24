package com.example.demo.service.impl;

import com.example.demo.enums.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImplementare implements ProductService {

    @Autowired
    ProductRepository productRepository;




    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);

    }

    @SneakyThrows
    @Override
    public void deteleProduct(Long product_id) {
        productRepository.findById(product_id).orElseThrow(()-> new Exception("sdv"));
        productRepository.deleteById(product_id);
    }

    @SneakyThrows
    @Override
    public void updateProduct(Product product, Long id) {
        productRepository.findById(id).orElseThrow(()->new Exception("Id not found"));
        product.setId(id);
        productRepository.save(product);
    }

    @Override
    public List<Product> findBySex(String sex) {
        return productRepository.findBySex(sex);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> findBySexBeforeAndCategory(String sex, Category category) {
        return productRepository.findBySexBeforeAndCategory(sex, category);
    }

    @Override
    public List<Product> findBySexBeforeAndCategoryAndBrand(String sex, Category category, String brand) {
        return productRepository.findBySexBeforeAndCategoryAndBrand(sex, category, brand);
    }

    @Override
    public List<Product> findBySexBeforeAndCategoryAndPrice(String sex, Category category, Long pret) {
        return productRepository.findBySexBeforeAndCategoryAndPrice(sex, category, pret);
    }

    @Override
    public Optional<Product> findById(long productId) {
        return productRepository.findById(productId);
    }

    /*@Override
    public List<Product> findBySexBeforeAndCategoryAndBrandOrderByPrice(String sex, Category category, String brand,Long price) {
        return productRepository.findBySexBeforeAndCategoryAndBrandOrderByPrice(sex, category, brand,price);
    }
    *//*@Override
    public List<Product> findBySexBeforeAndCategoryOrderByPrice(String sex, Category category,Long price) {
        return productRepository.findBySexBeforeAndCategoryOrderByPrice(sex, category, price);
    }*//*

    @Override
    public List<Product> findBySexOrderByPrice(String sex,Long price) {
        return productRepository.findBySexOrderByPrice(sex ,price);
    }
    @Override
    public List<Product> findByCategoryOrderByPrice( Category category, Long price) {
        return productRepository.findByCategoryOrderByPrice( category, price);
    }*/
}
