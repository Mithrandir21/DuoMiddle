package com.duopoints.controller;

import com.duopoints.db.tables.pojos.MediaObject;
import com.duopoints.db.tables.pojos.MediaObjectList;
import com.duopoints.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class MediaController {

    @Autowired
    private MediaService mediaService;


    /*****************
     * MEDIAOBJECT
     *****************/

    @RequestMapping(method = RequestMethod.GET, value = "/createMediaObject", produces = MediaType.APPLICATION_JSON_VALUE)
    public MediaObject createMediaObject(@RequestParam(required = false) String description, @RequestParam String type) {
        return mediaService.createMediaObject(description, type);
    }


    /********************
     * MEDIAOBJECTLIST
     ********************/

    @RequestMapping(method = RequestMethod.GET, value = "/createMediaObjectList", produces = MediaType.APPLICATION_JSON_VALUE)
    public MediaObjectList createMediaObjectList(@RequestParam UUID pointEventID, @RequestParam(required = false) UUID pointID, @RequestParam UUID mediaObjectID) {
        return mediaService.createMediaObjectList(pointEventID, pointID, mediaObjectID);
    }
}
