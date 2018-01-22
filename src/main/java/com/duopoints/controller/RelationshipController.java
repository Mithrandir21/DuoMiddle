package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.models.FullRelationshipData;
import com.duopoints.models.composites.CompositeRelationship;
import com.duopoints.models.composites.CompositeRelationshipBreakupRequest;
import com.duopoints.models.composites.CompositeRelationshipRequest;
import com.duopoints.models.posts.NewRelationshipBreakupRequest;
import com.duopoints.models.posts.NewRelationshipRequest;
import com.duopoints.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

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
    
    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    @RequestMapping(method = RequestMethod.POST, value = "/createCompositeRelationshipRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipRequest createCompositeRelationshipRequest(@RequestBody NewRelationshipRequest newRequest) {
        return relationshipService.createRelationshipRequest(newRequest);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalCompositeRelationshipRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositeRelationshipRequest setFinalCompositeRelationshipRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return relationshipService.setFinalRelationshipRequestStatus(requestID, finalStatus);
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