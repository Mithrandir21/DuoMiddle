package com.duopoints.controller;

import com.duopoints.db.tables.pojos.User;
import com.duopoints.errorhandling.NullResultException;
import com.duopoints.models.posts.UserReg;
import com.duopoints.service.InitService;
import com.duopoints.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private InitService initService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/regUser", method = RequestMethod.POST, consumes = "application/json")
    public User registerUser(@RequestBody UserReg userReg) {
        return userService.regUser(userReg);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@RequestParam UUID userID) {
        User foundUser = userService.getUser(userID);
        if (foundUser != null) {
            return foundUser;
        } else {
            throw new NullResultException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserPoints")
    public Integer getAllUserPoints(@RequestParam UUID userID){
        return userService.getAllUserPoint(userID);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/populateLevelReqs")
    public void populateLevelReq() {
        initService.populateLevelReq();
    }

//    public List<User> getFriends(@RequestBody UUID userID) {
//
//    }
}