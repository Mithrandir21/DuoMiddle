package com.duopoints.controller

import com.duopoints.Utils
import com.duopoints.models.composites.CompositeFriendship
import com.duopoints.models.composites.CompositeFriendshipRequest
import com.duopoints.models.posts.NewFriendshipRequest
import com.duopoints.service.FriendService
import com.duopoints.service.fcm.FcmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/")
class FriendController {

    @Autowired
    lateinit var friendService: FriendService

    @Autowired
    lateinit var fcmService: FcmService


    /*****************
     * FRIENDSHIP
     *****************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/getActiveCompositeFriendship"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getActiveCompositeFriendship(@RequestParam userOne: UUID, @RequestParam userTwo: UUID): CompositeFriendship =
            Utils.returnOrException(friendService.getActiveCompositeFriendship(userOne, userTwo))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getAllActiveCompositeFriendships"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllActiveCompositeFriendships(@RequestParam userID: UUID): List<CompositeFriendship> =
            Utils.returnOrException(friendService.getAllActiveCompositeFriendships(userID))


    /*****************
     * FRIEND REQUEST
     *****************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/getCompositeFriendRequest"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCompositeFriendRequest(@RequestParam friendRequestID: UUID): CompositeFriendshipRequest =
            Utils.returnOrException(friendService.getCompositeFriendRequest(friendRequestID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getAllActiveCompositeFriendRequests"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllActiveCompositeFriendRequests(@RequestParam userID: UUID): List<CompositeFriendshipRequest> =
            Utils.returnOrException(friendService.getAllActiveCompositeFriendRequests(userID))

    @RequestMapping(method = [RequestMethod.POST], value = ["/createCompositeFriendRequest"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createCompositeFriendRequest(@RequestBody newFriendshipRequest: NewFriendshipRequest): CompositeFriendshipRequest =
            fcmService.sendFriendRequestNotification(Utils.returnOrException(friendService.createCompositeFriendRequest(newFriendshipRequest)))

    @RequestMapping(method = [RequestMethod.PATCH], value = ["/setFinalCompositeFriendRequestStatus"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun setFinalCompositeFriendRequestStatus(@RequestParam requestID: UUID, @RequestParam finalStatus: String): CompositeFriendshipRequest =
            Utils.returnOrException(friendService.setFinalCompositeFriendRequestStatus(requestID, finalStatus))
}