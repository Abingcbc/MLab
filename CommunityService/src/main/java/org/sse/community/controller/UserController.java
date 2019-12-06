package org.sse.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sse.community.model.User;
import org.sse.community.service.UserService;

import java.util.List;

/**
 * @author HPY
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("check/{username}")
    public boolean checkUsername(@PathVariable String username){
        return userService.checkUser(username);
    }
}
