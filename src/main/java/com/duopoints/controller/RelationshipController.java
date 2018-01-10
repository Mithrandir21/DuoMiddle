package com.duopoints.controller;

import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.errorhandling.NullResultException;
import com.duopoints.models.RequestParameters;
import com.duopoints.models.posts.NewRelationship;
import com.duopoints.models.posts.NewRelationshipRequest;
import com.duopoints.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
        Relationship rel = relationshipService.getRelationship(relID);
        if (rel != null) {
            return rel;
        } else {
            throw new NullResultException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/createRelationship", produces = MediaType.APPLICATION_JSON_VALUE)
    public Relationship createRelationship(@RequestBody NewRelationship newRelationship){
        return relationshipService.createRelationship(newRelationship);
    }

    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    @RequestMapping(method = RequestMethod.POST, value = "/createRelationshipRequest", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipRequest createRelationshipRequest(@RequestBody NewRelationshipRequest newRequest){
        return relationshipService.createRelationshipRequest(newRequest);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getRelationshipRequestsSentByUser", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RelationshipRequest> getRelationshipRequestsSentByUser(@RequestParam UUID userID){
        return relationshipService.getRelationshipRequestsSentByUser(userID);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/setFinalRelationshipRequestStatus", produces = MediaType.APPLICATION_JSON_VALUE)
    public RelationshipRequest setFinalRelationshipRequestStatus(@RequestParam UUID requestID, @RequestParam String finalStatus){
        return relationshipService.setFinalRelationshipRequestStatus(requestID, finalStatus);
    }
}