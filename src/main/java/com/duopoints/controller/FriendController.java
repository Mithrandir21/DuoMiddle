package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.models.composites.gets.CompositeFriendRequest;
import com.duopoints.models.composites.gets.CompositeFriendship;
import com.duopoints.models.posts.NewFriendRequest;
import com.duopoints.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class FriendController {

    @Autowired
    private FriendService friendService;


    /*****************
     * FRIENDSHIP
     *****************/

    @RequestMapping(method = RequestMethod.GET, value = "/getActiveCompositeFriendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendship getActiveCompositeFriendship(@RequestParam UUID userOne, @RequestParam UUID userTwo) {
        return Utils.returnOrException(friendService.getActiveCompositeFriendship(userOne, userTwo));
    }


    /*****************
     * FRIEND REQUEST
     *****************/

    @RequestMapping(method = RequestMethod.GET, value = "/getCompositeFriendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendRequest getCompositeFriendRequest(@RequestParam UUID friendRequestID) {
        return Utils.returnOrException(friendService.getCompositeFriendRequest(friendRequestID));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCompositeFriendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendRequest createCompositeFriendRequest(@RequestBody NewFriendRequest newFriendRequest) {
        return Utils.returnOrException(friendService.createCompositeFriendRequest(newFriendRequest));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalCompositeFriendRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendRequest setFinalCompositeFriendRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return Utils.returnOrException(friendService.setFinalCompositeFriendRequestStatus(requestID, finalStatus));
    }
}