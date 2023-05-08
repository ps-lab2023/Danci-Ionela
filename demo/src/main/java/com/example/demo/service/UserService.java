package com.example.demo.service;

import com.example.demo.Dto.UserResponse;
import com.example.demo.model.User;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {


    void updateUser(User user, Long id);

    Optional<User> findById(Long id);

    UserResponse createUser(User user);

    List<UserResponse> findAll();

    User register(User user) ;

    User loginBuyer(User user) ;

    User loginAdmin(User user) ;

    UserResponse logOut(String email) ;

    User findByEmail(String email);

    @SneakyThrows
    void setLogedVar(Long id, boolean loged);

    @SneakyThrows
    boolean getLogedVar(String email);

    String forgotPassword(String email);

    void deleteById(Long id);
}
