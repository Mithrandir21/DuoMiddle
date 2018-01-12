package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.db.tables.pojos.FriendRequest;
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
     * FRIEND REQUEST
     *****************/

    @RequestMapping(method = RequestMethod.GET, value = "/getFriendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendRequest getFriendRequest(@RequestParam UUID friendRequestID) {
        return Utils.returnOrException(friendService.getFriendRequest(friendRequestID));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createFriendRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendRequest createFriendRequest(@RequestBody NewFriendRequest newFriendRequest) {
        return friendService.createFriendRequest(newFriendRequest);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalFriendRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendRequest setFinalFriendRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return friendService.setFinalFriendRequestStatus(requestID, finalStatus);
    }
}