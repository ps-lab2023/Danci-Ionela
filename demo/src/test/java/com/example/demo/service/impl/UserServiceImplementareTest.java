package com.example.demo.service.impl;

import com.example.demo.enums.ActivityType;
import com.example.demo.enums.Category;
import com.example.demo.enums.UserType;
import com.example.demo.model.ActivityLog;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityLogRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplementareTest {
    private UserServiceImplementare underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityLogRepository activityLogRepository;

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest=new UserServiceImplementare(userRepository,activityLogRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void whenGivenId_shouldUpdateProduct_ifFound() {
        User user=new User("User1@gmail.com","user1",UserType.BUYER);
        User newUser=new User("User2@gmail.com","user2",UserType.BUYER);
        given(underTest.findById(user.getUserId())).willReturn(Optional.of(user));
        underTest.updateUser(newUser,user.getUserId());
        verify(userRepository).save(newUser);
        verify(userRepository).findByUserId(user.getUserId());

    }
    @Test
    void should_throw_exception_when_product_doesnt_existUpdate() {
        User user=new User("User1@gmail.com","user1",UserType.BUYER);
        User newUser=new User("User2@gmail.com","user2",UserType.BUYER);
        user.setUserId(18L);
        newUser.setUserId(20L);
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.updateUser(newUser,user.getUserId());
        });

    }

    @Test
    void findAll() {
        //when
        underTest.findAll();

        //then
        verify(userRepository).findAll();
    }

    @Test
    void findByIdTest() {
        User user=new User("User1@gmail.com","user1",UserType.BUYER);
        user.setUserId(1L);
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));

        Optional<User> p=underTest.findById(1L);
        assert(p.isPresent());

        assert(1L == p.get().getUserId());
    }

    @Test
    void findByIdTestWhenIdDoesntExist() {
        when(userRepository.findByUserId(12L)).thenReturn(null);
        Optional<User> p=underTest.findById(12L);

        assert(isNull(p));
    }


    @Test
    void registerWhenCorrectEmailAndPassword() {
        ActivityLog entity = new ActivityLog();
        entity.setEmail("user1@gmail.com");
        entity.setActivity(ActivityType.REGISTER);
        when(activityLogRepository.save(ArgumentMatchers.any(ActivityLog.class))).thenReturn(entity);

        User created=underTest.register(new User(entity.getEmail(),"qwshgyx",UserType.BUYER));


        assert(created.getEmail()).equals(entity.getEmail());
    }
    @Test
    void registerWhenIncorrectEmailAndPassword() {
        ActivityLog entity = new ActivityLog();
        entity.setEmail("user1@gmail.com");
        entity.setActivity(ActivityType.REGISTER);
        when(activityLogRepository.save(ArgumentMatchers.any(ActivityLog.class))).thenReturn(entity);

        Exception exception = assertThrows(Exception.class, () -> {
            User created=underTest.register(new User(entity.getEmail(),"qwsx",UserType.BUYER));
        });
    }

    @Test
    void loginWhenCorrectData() {
        ActivityLog entity = new ActivityLog();
        entity.setEmail("user1@gmail.com");
        entity.setActivity(ActivityType.REGISTER);
        when(activityLogRepository.findByEmail(entity.getEmail())).thenReturn(entity);
        User user=new User(entity.getEmail(),"Userd",UserType.BUYER);
        user.setUserId(1L);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        User created=underTest.login(user);

        assert(created.equals(user));
    }

    @Test
    void loginWhenIncorrectData() {
        ActivityLog entity = new ActivityLog();
        entity.setEmail("user1@gmail.com");
        entity.setActivity(ActivityType.LOGIN);
        when(activityLogRepository.findByEmail(entity.getEmail())).thenReturn(entity);
        User user=new User(entity.getEmail(),"Userd",UserType.BUYER);
        when(userRepository.findByEmail(entity.getEmail())).thenReturn(user);

        Exception exception = assertThrows(Exception.class, () -> {
            User created=underTest.login(user);
        });
    }

    @Test
    void logOutCorrectData() {
        ActivityLog entity = new ActivityLog();
        entity.setEmail("user1@gmail.com");
        entity.setActivity(ActivityType.LOGIN);
        when(activityLogRepository.findByEmail(entity.getEmail())).thenReturn(entity);
        User user=new User(entity.getEmail(),"Userd",UserType.BUYER);
        user.setUserId(1L);
        when(activityLogRepository.save(entity)).thenReturn(entity);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        User created=underTest.logOut(user);

        assert(created.equals(user));
    }
    @Test
    void logOutIncorrectData() {
        ActivityLog entity = new ActivityLog();
        entity.setEmail("user1@gmail.com");
        entity.setActivity(ActivityType.REGISTER);
        when(activityLogRepository.findByEmail(entity.getEmail())).thenReturn(entity);
        User user=new User(entity.getEmail(),"Userd",UserType.BUYER);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));


        Exception exception = assertThrows(Exception.class, () -> {
            User created=underTest.logOut(user);
        });
    }
}