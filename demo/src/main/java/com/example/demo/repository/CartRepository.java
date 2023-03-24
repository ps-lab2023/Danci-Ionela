package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Cart;

import java.util.List;

@Repository
public interface CartRepository extends CrudRepository<Cart,Long> {
    public List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    public void deleteAllByUser(User user);
}
