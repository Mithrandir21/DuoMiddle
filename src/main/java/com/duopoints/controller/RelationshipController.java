package com.duopoints.controller;

import com.duopoints.RequestParameters;
import com.duopoints.Utils;
import com.duopoints.models.FullRelationshipData;
import com.duopoints.models.composites.CompositeRelationship;
import com.duopoints.models.composites.CompositeRelationshipBreakupRequest;
import com.duopoints.models.composites.CompositeRelationshipRequest;
import com.duopoints.models.posts.NewRelationshipBreakupRequest;
import com.duopoints.models.posts.NewRelationshipRequest;
import com.duopoints.service.RelationshipService;
import com.duopoints.service.fcm.FcmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @Autowired
    private FcmService fcmService;

    /*********************
     * RELATIONSHIP
     *********************/

    @RequestMapping(method = RequestMethod.GET, value = "/getCompositeRelationship", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationship getCompositeRelationship(@RequestParam UUID relID) {
        return Utils.returnOrException(relationshipService.getCompositeRelationship(relID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getUserActiveCompositeRelationship", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationship getUserActiveCompositeRelationship(@RequestParam UUID userID) {
        return Utils.returnOrException(relationshipService.getActiveUserCompositeRelationship(userID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFullRelationshipData", produces = MediaType.APPLICATION_JSON_VALUE)
    public FullRelationshipData getFullRelationshipData(@RequestParam UUID relID) {
        return Utils.returnOrException(relationshipService.getFullRelationshipData(relID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getFullRelationshipDataByGivingUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public FullRelationshipData getFullRelationshipDataByGivingUser(@RequestParam UUID relID, @RequestParam UUID givingUserID) {
        return Utils.returnOrException(relationshipService.getFullRelationshipData(relID, givingUserID));
    }

    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    @RequestMapping(method = RequestMethod.POST, value = "/createCompositeRelationshipRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipRequest createCompositeRelationshipRequest(@RequestBody NewRelationshipRequest newRequest) {
        return fcmService.sendRelationshipRequestNotification(relationshipService.createRelationshipRequest(newRequest));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllActiveCompositeRelationshipRequests", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositeRelationshipRequest> getAllActiveCompositeRelationshipRequests(@RequestParam UUID userID) {
        return Utils.returnOrException(relationshipService.getAllActiveCompositeRelationshipRequests(userID));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalCompositeRelationshipRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipRequest setFinalCompositeRelationshipRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        CompositeRelationshipRequest compositeRelationshipRequest = relationshipService.setFinalRelationshipRequestStatus(requestID, finalStatus);

        if (finalStatus.equals(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted)
                && compositeRelationshipRequest.getRelationshipRequestStatus().equals(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted)) {
            return fcmService.sendNewRelationshipNotification(compositeRelationshipRequest);
        } else {
            return fcmService.sendRelationshipRequestNegativeNotification(compositeRelationshipRequest);
        }
    }


    /*************************
     * RELATIONSHIP BREAKUP
     *************************/

    @RequestMapping(method = RequestMethod.POST, value = "/requestCompositeRelationshipBreakup", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipBreakupRequest requestCompositeRelationshipBreakup(@RequestBody NewRelationshipBreakupRequest newBreakupRequest) {
        return relationshipService.requestCompositeRelationshipBreakup(newBreakupRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getActiveCompositeRelationshipBreakupRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipBreakupRequest getActiveCompositeRelationshipBreakupRequest(@RequestParam UUID relID) {
        return Utils.returnOrException(relationshipService.getActiveCompositeRelationshipBreakup(relID));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalCompositeRelationshipBreakupRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipBreakupRequest setFinalCompositeRelationshipBreakupRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return relationshipService.setFinalRelationshipBreakupRequestStatus(requestID, finalStatus);
    }
}