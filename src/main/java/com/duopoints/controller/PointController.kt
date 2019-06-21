package com.duopoints.controller

import com.duopoints.Utils
import com.duopoints.db.tables.pojos.PointEventEmotion
import com.duopoints.db.tables.pojos.PointEventLike
import com.duopoints.db.tables.pojos.PointType
import com.duopoints.db.tables.pojos.PointTypeCategory
import com.duopoints.models.composites.CompositePointEvent
import com.duopoints.models.posts.NewPointEvent
import com.duopoints.service.PointService
import com.duopoints.service.fcm.FcmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/")
class PointController {

    @Autowired
    lateinit var pointService: PointService

    @Autowired
    lateinit var fcmService: FcmService


    /*******************
     * POINT TYPES
     *******************/

    val allActivePointTypes: List<PointType>
        @RequestMapping(method = [RequestMethod.GET], value = ["/getAllActivePointTypes"], produces = [MediaType.APPLICATION_JSON_VALUE])
        get() = pointService.allActivePointTypes


    /****************************
     * POINT TYPES CATEGORIES
     ****************************/

    val allActivePointTypeCategories: List<PointTypeCategory>
        @RequestMapping(method = [RequestMethod.GET], value = ["/getAllActivePointTypeCategories"], produces = [MediaType.APPLICATION_JSON_VALUE])
        get() = pointService.allActivePointTypeCategories


    /****************************
     * POINT EVENT EMOTION
     ****************************/

    val allActivePointEventEmotions: List<PointEventEmotion>
        @RequestMapping(method = [RequestMethod.GET], value = ["/getAllActivePointEventEmotions"], produces = [MediaType.APPLICATION_JSON_VALUE])
        get() = pointService.allActivePointEventEmotions


    @RequestMapping(method = [RequestMethod.GET], value = ["/getCompositePointsData"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCompositePointsData(@RequestParam pointEventID: UUID): CompositePointEvent =
            Utils.returnOrException(pointService.getCompositePointEvent(pointEventID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/getCompositePointEvents"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCompositePointEvents(@RequestParam relID: UUID): List<CompositePointEvent> = pointService.getCompositePointEvents(relID)

    @RequestMapping(method = [RequestMethod.POST], value = ["/givePoints"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun givePoints(@RequestBody newPointEvent: NewPointEvent): CompositePointEvent =
            // Send Push Notification to User that Received the points
            fcmService.sendPointEventPushNotification(Utils.returnOrException(pointService.givePoints(newPointEvent)))

    @RequestMapping(method = [RequestMethod.GET], value = ["/searchForActivePointTypes"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun searchForActivePointTypes(@RequestParam query: String): List<PointType> = pointService.searchForActivePointTypes(query)


    /*********************
     * POINT EVENT LIKES
     *********************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/likedPointEvents"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun likedPointEvents(@RequestParam userID: UUID): List<UUID> = pointService.likedPointEvents(userID)

    @RequestMapping(method = [RequestMethod.GET], value = ["/likePointEvent"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun likeEvent(@RequestParam eventID: UUID, @RequestParam userID: UUID): PointEventLike =
            fcmService.sendLikeNotification(Utils.returnOrException(pointService.likeEvent(eventID, userID)))

    @RequestMapping(method = [RequestMethod.GET], value = ["/unlikePointEvent"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun unlikeEvent(@RequestParam eventID: UUID, @RequestParam userID: UUID): PointEventLike =
            Utils.returnOrException(pointService.unlikeEvent(eventID, userID))
}