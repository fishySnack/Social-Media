package com.showly.social_media.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import com.showly.social_media.Data.User;
import com.showly.social_media.Repository.UserRepo;

@Slf4j
@Service
public class UserService {

    private final UserRepo repo;
    private int startingSearch = 10;

    public UserService(UserRepo repo){
        this.repo = repo;
    }

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

    public boolean deleteUser(Long id){

        Optional<User> target = repo.findById(id);
        if(target.isEmpty()){
            log.error("delete user target not found");
            return false;
        }

        repo.deleteById(id);

        return true;
    }

    public boolean deleteUser(User user){
        return deleteUser(user.getId());
    }

    public boolean updateUser(Long id, User updatedInfo){
        Optional<User> isFound = repo.findById(id);

        if(isFound.isEmpty()) {
            log.error("update user not found");
            return false;// should throw an error, will add later
        }

        User target = isFound.get();

        target.setBio(updatedInfo.getBio());
        target.setName(updatedInfo.getName());
        target.setUsername(updatedInfo.getUsername());

        repo.save(target);
        return true;
    }
    
}
