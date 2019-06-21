package com.duopoints.controller

import com.duopoints.RequestParameters
import com.duopoints.Utils
import com.duopoints.models.FullRelationshipData
import com.duopoints.models.composites.CompositeRelationship
import com.duopoints.models.composites.CompositeRelationshipBreakupRequest
import com.duopoints.models.composites.CompositeRelationshipRequest
import com.duopoints.models.posts.NewRelationshipBreakupRequest
import com.duopoints.models.posts.NewRelationshipRequest
import com.duopoints.service.RelationshipService
import com.duopoints.service.fcm.FcmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/")
class RelationshipController {

    @Autowired
    lateinit var relationshipService: RelationshipService

    @Autowired
    lateinit var fcmService: FcmService

    /*********************
     * RELATIONSHIP
     *********************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/getCompositeRelationship"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCompositeRelationship(@RequestParam relID: UUID): CompositeRelationship =
            Utils.returnOrException(relationshipService.getCompositeRelationship(relID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getUserActiveCompositeRelationship"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserActiveCompositeRelationship(@RequestParam userID: UUID): CompositeRelationship =
            Utils.returnOrException(relationshipService.getActiveUserCompositeRelationship(userID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getFullRelationshipData"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFullRelationshipData(@RequestParam relID: UUID): FullRelationshipData = Utils.returnOrException(relationshipService.getFullRelationshipData(relID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getFullRelationshipDataByGivingUser"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFullRelationshipDataByGivingUser(@RequestParam relID: UUID, @RequestParam givingUserID: UUID): FullRelationshipData =
            Utils.returnOrException(relationshipService.getFullRelationshipData(relID, givingUserID))

    
    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    @RequestMapping(method = [RequestMethod.POST], value = ["/createCompositeRelationshipRequest"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createCompositeRelationshipRequest(@RequestBody newRequest: NewRelationshipRequest): CompositeRelationshipRequest =
            fcmService.sendRelationshipRequestNotification(relationshipService.createRelationshipRequest(newRequest))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getAllActiveCompositeRelationshipRequests"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllActiveCompositeRelationshipRequests(@RequestParam userID: UUID): List<CompositeRelationshipRequest> =
            Utils.returnOrException(relationshipService.getAllActiveCompositeRelationshipRequests(userID))

    @RequestMapping(method = [RequestMethod.PATCH], value = ["/setFinalCompositeRelationshipRequestStatus"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun setFinalCompositeRelationshipRequestStatus(@RequestParam requestID: UUID, @RequestParam finalStatus: String): CompositeRelationshipRequest {
        val compositeRelationshipRequest = relationshipService.setFinalRelationshipRequestStatus(requestID, finalStatus)

        return if (finalStatus == RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted && compositeRelationshipRequest.relationshipRequestStatus == RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted) {
            fcmService.sendNewRelationshipNotification(compositeRelationshipRequest)
        } else {
            fcmService.sendRelationshipRequestNegativeNotification(compositeRelationshipRequest)
        }
    }


    /*************************
     * RELATIONSHIP BREAKUP
     *************************/

    @RequestMapping(method = [RequestMethod.POST], value = ["/requestCompositeRelationshipBreakup"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun requestCompositeRelationshipBreakup(@RequestBody newBreakupRequest: NewRelationshipBreakupRequest): CompositeRelationshipBreakupRequest =
            fcmService.sendRelationshipBreakupUpdate(relationshipService.requestCompositeRelationshipBreakup(newBreakupRequest))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getActiveCompositeRelationshipBreakupRequest"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getActiveCompositeRelationshipBreakupRequest(@RequestParam relID: UUID): CompositeRelationshipBreakupRequest =
            Utils.returnOrException(relationshipService.getActiveCompositeRelationshipBreakup(relID))

    @RequestMapping(method = [RequestMethod.PATCH], value = ["/setFinalCompositeRelationshipBreakupRequestStatus"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun setFinalCompositeRelationshipBreakupRequestStatus(@RequestParam requestID: UUID, @RequestParam finalStatus: String): CompositeRelationshipBreakupRequest =
            fcmService.sendRelationshipBreakupUpdate(relationshipService.setFinalRelationshipBreakupRequestStatus(requestID, finalStatus))
}