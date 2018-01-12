package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.db.tables.pojos.User;
import com.duopoints.db.tables.pojos.UserAddress;
import com.duopoints.db.tables.pojos.UserLevel;
import com.duopoints.models.posts.UserReg;
import com.duopoints.service.InitService;
import com.duopoints.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

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
        return Utils.returnOrException(userService.getUser(userID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserPoints")
    public ResponseEntity getAllUserPoints(@RequestParam UUID userID) {
        return new ResponseEntity<>(Collections.singletonMap("total_points", userService.getAllUserPoint(userID)), HttpStatus.OK);
    }


    /************
     * USER ADR
     ************/

    @RequestMapping(method = RequestMethod.GET, value = "/getUserAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAddress getUserAddress(@RequestParam UUID userAdrID) {
        return Utils.returnOrException(userService.getUserAddress(userAdrID));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updateUserAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAddress updateUserAddress(@RequestBody UserAddress userAddress) {
        return Utils.returnOrException(userService.updateUserAddress(userAddress));
    }


    /************
     * USER ADR
     ************/

    @RequestMapping(method = RequestMethod.GET, value = "/getUserLevel", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLevel getUserLevel(@RequestParam UUID userAdrID) {
        return Utils.returnOrException(userService.getUserLevel(userAdrID));
    }
}