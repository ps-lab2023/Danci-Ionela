package com.example.demo.service.impl;

import com.example.demo.Dto.AddToCartDto;
import com.example.demo.Dto.CartDto;
import com.example.demo.Dto.CartItemDto;
import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
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
public class CartServiceImplementare implements CartService {
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    private EmailServiceImplementare emailServiceImplementare;

    @SneakyThrows
    @Override
    public Cart addToCart(AddToCartDto addToCartDto,String email){
        Optional<Product> optionalProduct= productService.findById(addToCartDto.getProductId());
        if(!optionalProduct.isPresent())
            throw new Exception("Product not found");
        Product product=optionalProduct.get();
        User user=userService.findByEmail(email);
        List<Cart> cartList=cartRepository.findAllByUserAndProduct(user,product);
        if(cartList.size()>=1) {
            increment_product_quantity(email,product.getId());
            return cartList.get(0);
        }
        Cart cart=new Cart(new Date(),product,user, addToCartDto.getQuantity());
        return cartRepository.save(cart);
    }

    @Override
    public CartDto listCartItems(String email){
        User user=userService.findByEmail(email);
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItemDtoList=new ArrayList<>();
        double totalCost=0;
        for(Cart cart:cartList){
            if(!isNull(cart)) {
                CartItemDto c = new CartItemDto(Math.toIntExact(cart.getCart_id()), cart.getQuantity(), cart.getProduct());

                if(!isNull(c.getProduct())){
                    cartItemDtoList.add(c);
                    totalCost = totalCost + c.getProduct().getPrice() * c.getQuantity();
            }}
            }
        CartDto cartDto=new CartDto();
        cartDto.setCartItems(cartItemDtoList);
        cartDto.setTotalCost(totalCost);
        return cartDto;
    }

    @SneakyThrows
    @Override
    public void sendEmail(String email){
        CartDto cartDto=listCartItems(email);
        emailServiceImplementare.sendMailWithAttachment(email,
                "Order Confirmation " + cartDto.prettyPrint() + "Thank you for your order!\n\nShoes Shop",
                "Order confirmation");
    }

    @SneakyThrows
    @Override
    public void deleteCartItem(String email,Long productId)  {
        Optional<Product> optionalProduct= productService.findById(productId);
        if(!optionalProduct.isPresent())
            throw new Exception("Product not found");
        Product product=optionalProduct.get();
        User user=userService.findByEmail(email);
        cartRepository.deleteAllByUserAndProduct(user,product);
    }

    @Override
    public void deleteAllCartItems(String userEmail){
        User user=userService.findByEmail(userEmail);
        cartRepository.deleteAllByUser(user);
    }

    @SneakyThrows
    public void increment_product_quantity(String email, Long productId){
        User user=userService.findByEmail(email);
        Product product=productService.findById(productId).orElseThrow(()->new Exception("Id not found"));
        List<Cart> cartList=cartRepository.findAllByUserAndProduct(user,product);
        Cart cart= cartList.get(0);
        cart.setQuantity(cart.getQuantity() + 1 );
        cartRepository.save(cart);
    }

    @SneakyThrows
    public void decrement_product_quantity(String email, Long productId){
        User user=userService.findByEmail(email);
        Product product=productService.findById(productId).orElseThrow(()->new Exception("Id not found"));
        List<Cart> cartList=cartRepository.findAllByUserAndProduct(user,product);
        Cart cart= cartList.get(0);
        cart.setQuantity(cart.getQuantity() - 1 );
        cartRepository.save(cart);
    }
}
