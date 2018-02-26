package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.db.tables.pojos.PointEventEmotion;
import com.duopoints.db.tables.pojos.PointEventLike;
import com.duopoints.db.tables.pojos.PointType;
import com.duopoints.db.tables.pojos.PointTypeCategory;
import com.duopoints.models.composites.CompositePointEvent;
import com.duopoints.models.posts.NewPointEvent;
import com.duopoints.service.PointService;
import com.duopoints.service.fcm.FcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class PointController {

    @Autowired
    private PointService pointService;

    @Autowired
    private FcmService fcmService;


    @RequestMapping(method = RequestMethod.GET, value = "/getCompositePointsData", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositePointEvent getCompositePointsData(@RequestParam UUID pointEventID) {
        return Utils.returnOrException(pointService.getCompositePointEvent(pointEventID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCompositePointEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositePointEvent> getCompositePointEvents(@RequestParam UUID relID) {
        return pointService.getCompositePointEvents(relID);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/givePoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositePointEvent givePoints(@RequestBody NewPointEvent newPointEvent) {
        // Send Push Notification to User that Received the points
        return fcmService.sendPointEventPushNotification(Utils.returnOrException(pointService.givePoints(newPointEvent)));
    }


    /*******************
     * POINT TYPES
     *******************/

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActivePointTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PointType> getAllActivePointTypes() {
        return pointService.getAllActivePointTypes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/searchForActivePointTypes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PointType> searchForActivePointTypes(@RequestParam String query) {
        return pointService.searchForActivePointTypes(query);
    }


    /****************************
     * POINT TYPES CATEGORIES
     ****************************/

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActivePointTypeCategories", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PointTypeCategory> getAllActivePointTypeCategories() {
        return pointService.getAllActivePointTypeCategories();
    }


    /****************************
     * POINT EVENT EMOTION
     ****************************/

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActivePointEventEmotions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PointEventEmotion> getAllActivePointEventEmotions() {
        return pointService.getAllActivePointEventEmotions();
    }


    /*********************
     * POINT EVENT LIKES
     *********************/

    @RequestMapping(method = RequestMethod.GET, value = "/likedPointEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UUID> likedPointEvents(@RequestParam UUID userID) {
        return pointService.likedPointEvents(userID);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/likePointEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public PointEventLike likeEvent(@RequestParam UUID eventID, @RequestParam UUID userID) {
        return fcmService.sendLikeNotification(Utils.returnOrException(pointService.likeEvent(eventID, userID)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/unlikePointEvent", produces = MediaType.APPLICATION_JSON_VALUE)
    public PointEventLike unlikeEvent(@RequestParam UUID eventID, @RequestParam UUID userID) {
        return Utils.returnOrException(pointService.unlikeEvent(eventID, userID));
    }
}