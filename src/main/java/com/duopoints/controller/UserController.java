package com.duopoints.controller;

import com.duopoints.db.tables.pojos.User;
import com.duopoints.db.tables.pojos.UserAddress;
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

    @RequestMapping(method = RequestMethod.GET, value = "/populateLevelReqs")
    public void populateLevelReq() {
        initService.populateLevelReq();
    }

    /************
     * USER
     ************/

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

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserPoints", produces = MediaType.TEXT_PLAIN_VALUE)
    public Integer getAllUserPoints(@RequestParam UUID userID){
        return userService.getAllUserPoint(userID);
    }



    /************
     * USER ADR
     ************/

    @RequestMapping(method = RequestMethod.GET, value = "/getUserAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAddress getUserAddress(@RequestParam UUID userAdrID){
        UserAddress userAddress = userService.getUserAddress(userAdrID);
        if (userAddress != null) {
            return userAddress;
        } else {
            throw new NullResultException();
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updateUserAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAddress updateUserAddress(@RequestBody UserAddress userAddress){
        UserAddress userAddressUpdated = userService.updateUserAddress(userAddress);
        if (userAddressUpdated != null) {
            return userAddressUpdated;
        } else {
            throw new NullResultException();
        }
    }
}