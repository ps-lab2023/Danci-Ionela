package com.example.demo.controller;
import com.example.demo.Dto.ProductDto;
import com.example.demo.enums.Category;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/all_products")
    public List<ProductDto> findAll() {
        return productService.getAllProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        ProductDto productDto=new ProductDto(createdProduct);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdProduct)
                .toUri();
        return ResponseEntity.created(uri).body(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deteleProduct(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@Valid @RequestBody Product product, @PathVariable Long id) {
        productService.updateProduct(product,id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(productService.findById(id));
    }

    @GetMapping("/find1")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> findBySexAndCat(@RequestParam String sex,@RequestParam Category category) {
        return productService.findBySexBeforeAndCategory(sex,category);

    }

    @GetMapping("/find2")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> find2(@RequestParam String sex, @RequestParam Category category,@RequestParam String brand) {
        return productService.findBySexBeforeAndCategoryAndBrand(sex,category,brand);
    }

    @GetMapping("/find3")
    public List<ProductDto> findAllBySexAndCategoryAndBrandOrderByPriceAsc(@RequestParam String sex,@RequestParam Category category,@RequestParam String brand){
        return productService.findAllBySexAndCategoryAndBrandOrderByPriceAsc(sex,category,brand);
    }
    @GetMapping("/find4")
    public List<ProductDto> findAllBySexAndCategoryAndBrandOrderByPriceDesc(@RequestParam String sex,@RequestParam Category category,@RequestParam String brand){
        return productService.findAllBySexAndCategoryAndBrandOrderByPriceDesc(sex,category,brand);
    }
    @GetMapping("/find5")
    public List<ProductDto> findAllBySexAndCategoryOrderByPriceAsc(@RequestParam String sex,@RequestParam Category category){
        return productService.findAllBySexAndCategoryOrderByPriceAsc(sex,category);
    }
    @GetMapping("/find6")
    public List<ProductDto> findAllBySexAndCategoryOrderByPriceDesc(@RequestParam String sex,@RequestParam Category category){
        return productService.findAllBySexAndCategoryOrderByPriceDesc(sex,category);
    }

    @GetMapping("/findByNameLike")
    public List<ProductDto> findAllByNameLike(@RequestParam String name){
        return productService.findAllByNameLike(name);
    }
}
