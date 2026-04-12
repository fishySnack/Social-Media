package com.showly.social_media.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.showly.social_media.Data.User;
import com.showly.social_media.Repository.UserRepo;

@Slf4j
@Service
public class UserService {

    private final UserRepo repo;

    public UserService(UserRepo repo){
        this.repo = repo;
    }

    private int startingSearch = 10;

    public User saveUser(User user){
        log.info("Saving user: " + user.getUsername());
        return repo.save(user);
    }
    
    public List<User> searchLimited(){
        return null;
    }

    public List<User> searchByString(String searchBy){
        return null;
    }
    
}
