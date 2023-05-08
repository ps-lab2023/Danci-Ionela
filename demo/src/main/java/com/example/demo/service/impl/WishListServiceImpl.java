package com.example.demo.service.impl;
import com.example.demo.Dto.ProductDto;
import com.example.demo.Dto.WishListDto;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.WishList;
import com.example.demo.repository.WishListRepo;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.service.WishListService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class WishListServiceImpl implements WishListService {
    @Autowired
    WishListRepo wishListRepo;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @SneakyThrows
    @Override
    public WishListDto createWishList(String email, Long productId){
        User user=userService.findByEmail(email);
        Optional<Product> optionalProduct=productService.findById(productId);
        if(!optionalProduct.isPresent()){
            throw new Exception("Not present");
        }
        Product product=optionalProduct.get();
        if(!isNull(wishListRepo.findAllByUserAndProduct(user,product))) return null;
        WishList wishList=new WishList(user,new Date(),product);
        wishListRepo.save(wishList);
        return new WishListDto(wishList);
    }

    @SneakyThrows
    @Override
    public List<ProductDto> getWishList(String email){
        User user=userService.findByEmail(email);
        final List<WishList> wishLists =wishListRepo.findAllByUserOrderByCreatedDateDesc(user);
        List<Product> productList=new ArrayList<>();
        for(WishList w:wishLists) {
            productList.add(w.getProduct());
        }
        List<ProductDto> productDtoList=new ArrayList<>();
        for(Product product:productList){
            if(!isNull(product))
                productDtoList.add(new ProductDto(product));
        }
        return productDtoList;
    }

    @Override
    public Optional<WishList> findById(Long id){
        return wishListRepo.findById(id);
    }

    @SneakyThrows
    @Override
    public void deleteWishList(Long id){
        wishListRepo.findByWishlistId(id).orElseThrow(()->new Exception("Id not found"));
        wishListRepo.deleteByWishlistId(id);
    }

    @SneakyThrows
    @Override
    public void delete_from_wishlist(String email,Long productId){
        User user=userService.findByEmail(email);
        Product product=productService.findById(productId).orElseThrow(()->new Exception("Id not found"));
        wishListRepo.deleteAllByUserAndProduct(user,product);
    }

}
