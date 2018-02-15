package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.db.tables.pojos.UserAddress;
import com.duopoints.db.tables.pojos.Userdata;
import com.duopoints.models.composites.CompositePointEvent;
import com.duopoints.models.posts.UserReg;
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

    @RequestMapping(method = RequestMethod.POST, value = "/regUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Userdata registerUser(@RequestBody UserReg userReg) {
        return userService.regUser(userReg);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public Userdata getUser(@RequestParam UUID userID) {
        return Utils.returnOrException(userService.getUser(userID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchForUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Userdata> searchForUser(@RequestParam String query) {
        return userService.searchForUser(query);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/updateUserAddress", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUserAddress(@RequestBody UserAddress userAddress) {
        return new ResponseEntity<>(Collections.singletonMap("success", userService.updateUserAddress(userAddress)), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserWithAuthID", produces = MediaType.APPLICATION_JSON_VALUE)
    public Userdata getUserWithAuthID(@RequestParam String userAuthID){
        return Utils.returnOrException(userService.getUserWithAuthID(userAuthID));
    }


    /************
     * FEED
     ************/

    @RequestMapping(method = RequestMethod.GET, value = "/getUserFeed", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositePointEvent> getUserFeed(@RequestParam UUID userID) {
        return userService.getUsersFeed(userID);
    }
}