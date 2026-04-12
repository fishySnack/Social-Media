package com.showly.social_media.RestController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showly.social_media.Data.User;
import com.showly.social_media.Service.UserService;

@RestController
@RequestMapping("/homepage")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    /**for friends
    my idea: if the user isnt searching up anything then, it finds 10 random people from database
    until the user scrolls down then, it gets 10 more random people

    search stuff!!!! 
    **/
    @GetMapping("/f")
    public List<User> getUsers(@RequestParam(required = false) String query) {
        if (query != null) {
            return userService.searchByString(query);
        }
        return userService.searchLimited();
    }
    
}
