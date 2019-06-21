package com.duopoints.controller

import com.duopoints.db.tables.pojos.MediaObject
import com.duopoints.db.tables.pojos.MediaObjectList
import com.duopoints.service.MediaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.util.UUID

@RestController
@RequestMapping("/")
class MediaController {

    @Autowired
    lateinit var mediaService: MediaService

    /*****************
     * MEDIAOBJECT
     *****************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/createMediaObject"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createMediaObject(@RequestParam(required = false) description: String, @RequestParam type: String): MediaObject =
            mediaService.createMediaObject(description, type)


    /********************
     * MEDIAOBJECTLIST
     *****************/

    @RequestMapping(method = [RequestMethod.GET], value = ["/createMediaObjectList"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createMediaObjectList(@RequestParam pointEventID: UUID, @RequestParam(required = false) pointID: UUID, @RequestParam mediaObjectID: UUID): MediaObjectList =
            mediaService.createMediaObjectList(pointEventID, pointID, mediaObjectID)
}
