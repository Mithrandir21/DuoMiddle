package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.RelationshipBreakupRequest;
import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.models.posts.NewRelationshipBreakupRequest;
import com.duopoints.models.posts.NewRelationshipRequest;
import com.duopoints.service.RelationshipService;
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

    /*********************
     * RELATIONSHIP
     *********************/

    @RequestMapping(method = RequestMethod.GET, value = "/getRelationship", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relationship getRelationship(@RequestParam UUID relID) {
        return Utils.returnOrException(relationshipService.getRelationship(relID));
    }

    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    @RequestMapping(method = RequestMethod.POST, value = "/createRelationshipRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipRequest createRelationshipRequest(@RequestBody NewRelationshipRequest newRequest) {
        return relationshipService.createRelationshipRequest(newRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRelationshipRequestsSentByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RelationshipRequest> getRelationshipRequestsSentByUser(@RequestParam UUID userID) {
        return relationshipService.getRelationshipRequestsSentByUser(userID);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalRelationshipRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipRequest setFinalRelationshipRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return relationshipService.setFinalRelationshipRequestStatus(requestID, finalStatus);
    }


    /*************************
     * RELATIONSHIP BREAKUP
     *************************/

    @RequestMapping(method = RequestMethod.GET, value = "/getActiveRelationshipBreakupRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipBreakupRequest getActiveRelationshipBreakupRequest(@RequestParam UUID relID) {
        return Utils.returnOrException(relationshipService.getActiveRelationshipBreakup(relID));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/requestRelationshipBreakup", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipBreakupRequest requestRelationshipBreakup(@RequestBody NewRelationshipBreakupRequest newBreakupRequest) {
        return relationshipService.requestRelationshipBreakup(newBreakupRequest);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalRelBreakupRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipBreakupRequest setFinalRelBreakupRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus) {
        return relationshipService.setFinalRelBreakupRequestStatus(requestID, finalStatus);
    }
}