package com.duopoints.controller

import com.duopoints.Utils
import com.duopoints.db.tables.pojos.UserAddress
import com.duopoints.db.tables.pojos.UserLevelUpLike
import com.duopoints.db.tables.pojos.Userdata
import com.duopoints.models.UserFeed
import com.duopoints.models.auth.UserAuthInfo
import com.duopoints.models.auth.UserAuthWrapper
import com.duopoints.models.auth.UserAuthenticatedResponse
import com.duopoints.models.posts.UserReg
import com.duopoints.service.UserService
import com.duopoints.service.fcm.FcmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import java.util.Collections
import java.util.UUID

@RestController
@RequestMapping("/")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var fcmService: FcmService


    /************
     * AUTH
     ************/

    @RequestMapping(method = [RequestMethod.POST], value = ["/authUser"])
    fun authUser(@RequestBody wrapper: UserAuthWrapper): UserAuthenticatedResponse = UserAuthenticatedResponse(userService.authUserJWT(wrapper))


    /************
     * USER
     ************/

    @RequestMapping(method = [RequestMethod.POST], value = ["/regUser"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody userReg: UserReg): Userdata = userService.regUser(userReg)

    @RequestMapping(method = [RequestMethod.GET], value = ["/getUser"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUser(@RequestParam userID: UUID): Userdata = Utils.returnOrException(userService.getUser(userID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/searchForUser"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun searchForUser(@RequestParam query: String): List<Userdata> = userService.searchForUser(query)

    @RequestMapping(method = [RequestMethod.PATCH], value = ["/updateUserAddress"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUserAddress(@RequestBody userAddress: UserAddress): ResponseEntity<*> =
            ResponseEntity(Collections.singletonMap("success", userService.updateUserAddress(userAddress)), HttpStatus.OK)

    @RequestMapping(method = [RequestMethod.GET], value = ["/getUserWithAuthID"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserWithAuthID(@RequestParam userAuthID: String): Userdata = Utils.returnOrException(userService.getUserWithAuthID(userAuthID))


    /*********************
     * LEVEL UP LIKES
     *********************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/likedLevelUp"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun likedPointEvents(@RequestParam userID: UUID): List<UUID> = userService.likedLevelUp(userID)

    @RequestMapping(method = [RequestMethod.GET], value = ["/likeLevelUp"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun likeEvent(@RequestParam levelUpID: UUID, @RequestParam userID: UUID): UserLevelUpLike =
            Utils.returnOrException(userService.likeLevelUp(levelUpID, userID))

    @RequestMapping(method = [RequestMethod.GET], value = ["/unlikeLevelUp"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun unlikeEvent(@RequestParam levelUpID: UUID, @RequestParam userID: UUID): UserLevelUpLike =
            Utils.returnOrException(userService.unlikeLevelUp(levelUpID, userID))


    /************
     * FEED
     ************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/getUserFeed"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserFeed(@RequestParam userID: UUID): UserFeed = userService.getUsersFeed(userID)
}