package com.example.demo.service.impl;

import com.example.demo.enums.ActivityType;
import com.example.demo.enums.UserType;
import com.example.demo.model.ActivityLog;
import com.example.demo.model.User;
import com.example.demo.repository.ActivityLogRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImplementare implements UserService {

    private final UserRepository userRepo;
    private final ActivityLogRepository activityLogEntityRepo;



    @SneakyThrows
    @Override
    public void updateUser(User user, Long id){
        userRepo.findByUserId(id).orElseThrow(()->new Exception("Id not found"));
        user.setUserId(id);
        userRepo.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepo.findByUserId(id);
    }


    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public List<User> findAll() {
        return  (List<User>) userRepo.findAll();

    }


    @SneakyThrows
    @Override
    public User register(User user1) {

        ActivityLog entity = new ActivityLog();
        User user=new User(user1.getEmail(),user1.getPassword(),UserType.BUYER);
        if(user1.getEmail().length()<5 || user1.getPassword().length()<5){
            entity.setActivity(ActivityType.INVALID_REGISTER);
            entity.setEmail("---");
            activityLogEntityRepo.save(entity);
            throw new Exception("Invalid Register");
        }
        entity.setEmail(user.getEmail());
        entity.setActivity(ActivityType.REGISTER);
        entity.setUser(user1);
        activityLogEntityRepo.save(entity);

        return user;
    }

    @SneakyThrows
    public User login(User user) {
        Optional<User> optionalUser = userRepo.findById(user.getUserId());

        ActivityLog entity = activityLogEntityRepo.findByEmail(user.getEmail());
        if(!Objects.nonNull(optionalUser)){
            entity.setActivity(ActivityType.INVALID_LOGIN);
            throw new Exception("Invalid email");
        }
        User user1=optionalUser.get();
        if(!user1.getPassword().equals(user.getPassword()) || !user1.getRole().equals(user.getRole())){
            entity.setActivity(ActivityType.INVALID_LOGIN);
            throw new Exception("Invalid password/role");
        }
        if(entity.getActivity().equals(ActivityType.LOGIN)){
            throw new Exception("Already loged in");
        }
        entity.setActivity(ActivityType.LOGIN);
        activityLogEntityRepo.save(entity);
        return user1;
    }

    @Override
    public User loginBuyer(User user) throws Exception {
        return login(user);
    }

    @Override
    public User loginAdmin(User user) throws Exception {
        return login(user);
    }

    @SneakyThrows
    @Override
    public User logOut(User user) {
        Optional<User> optionalUser = userRepo.findById(user.getUserId());
        ActivityLog entity = activityLogEntityRepo.findByEmail(user.getEmail());
        if(!optionalUser.isPresent() || !Objects.nonNull(entity)){
            throw new Exception("Invalid ");
        }
        User user1=optionalUser.get();
        if(!entity.getActivity().equals(ActivityType.LOGIN)){
            throw new Exception("not conected ");
        }
        entity.setActivity(ActivityType.SIGN_OUT);
        activityLogEntityRepo.save(entity);
        return user1;
    }

}
