package com.example.demo.service.impl;
import com.example.demo.Dto.UserResponse;
import com.example.demo.enums.UserType;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static java.util.Objects.isNull;
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

    @BeforeEach
    void setUp(){
        autoCloseable= MockitoAnnotations.openMocks(this);
        underTest=new UserServiceImplementare(userRepository);
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
        underTest.findAll();
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
        String email="user1@gmail.com";
        User user=new User(email,"qwshgyx",UserType.BUYER);
        user.setUserId(1L);
        when(userRepository.findByUserId(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User created=underTest.register(user);
        assert(created.getEmail()).equals(email);
    }

    @Test
    void registerWhenIncorrectEmailAndPassword() {
        String email="user1@gmail.com";
        Exception exception = assertThrows(Exception.class, () -> {
            User created=underTest.register(new User(email,"qwsx",UserType.BUYER));
        });
    }

    @Test
    void loginWhenCorrectData() {
        String email="user1@gmail.com";
        User user=new User(email,"Userd",UserType.BUYER);
        user.setUserId(1L);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User created=underTest.login(user);
        assert(created.equals(user));
    }

    @Test
    void loginWhenIncorrectData() {
        String email="user1@gmail.com";
        User user=new User(email,"Userd",UserType.BUYER);
        when(userRepository.findByEmail(email)).thenReturn(user);
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.login(user);
        });
    }

    @Test
    void logOutCorrectData() {
        String email="user1@gmail.com";
        User user=new User(email,"Userd",UserType.BUYER);
        user.setUserId(1L);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        UserResponse created=underTest.logOut(user.getEmail());
        UserResponse userR=new UserResponse((user));
        assert(created.equals(userR));
    }

    @Test
    void logOutIncorrectData() {
        String email="user1@gmail.com";
        User user=new User(email,"Userd",UserType.BUYER);
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        Exception exception = assertThrows(Exception.class, () -> {
            underTest.logOut(user.getEmail());
        });
    }
}