package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.models.composites.CompositeFriendship;
import com.duopoints.models.composites.CompositeFriendshipRequest;
import com.duopoints.models.posts.NewFriendshipRequest;
import com.duopoints.service.FriendService;
import com.duopoints.service.fcm.FcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private FcmService fcmService;


    /*****************
     * FRIENDSHIP
     *****************/

    @RequestMapping(method = RequestMethod.GET, value = "/getActiveCompositeFriendship", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendship getActiveCompositeFriendship(@RequestParam UUID userOne, @RequestParam UUID userTwo) {
        return Utils.returnOrException(friendService.getActiveCompositeFriendship(userOne, userTwo));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActiveCompositeFriendships", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositeFriendship> getAllActiveCompositeFriendships(@RequestParam UUID userID) {
        return Utils.returnOrException(friendService.getAllActiveCompositeFriendships(userID));
    }


    /*****************
     * FRIEND REQUEST
     *****************/

    @RequestMapping(method = RequestMethod.GET, value = "/getCompositeFriendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendshipRequest getCompositeFriendRequest(@RequestParam UUID friendRequestID) {
        return Utils.returnOrException(friendService.getCompositeFriendRequest(friendRequestID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActiveCompositeFriendRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositeFriendshipRequest> getAllActiveCompositeFriendRequests(@RequestParam UUID userID) {
        return Utils.returnOrException(friendService.getAllActiveCompositeFriendRequests(userID));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createCompositeFriendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendshipRequest createCompositeFriendRequest(@RequestBody NewFriendshipRequest newFriendshipRequest) {
        return fcmService.sendFriendRequestNotification(Utils.returnOrException(friendService.createCompositeFriendRequest(newFriendshipRequest)));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalCompositeFriendRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeFriendshipRequest setFinalCompositeFriendRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return Utils.returnOrException(friendService.setFinalCompositeFriendRequestStatus(requestID, finalStatus));
    }
}