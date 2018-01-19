package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.models.composites.gets.CompositePointEvent;
import com.duopoints.models.composites.posts.NewPointEvent;
import com.duopoints.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class PointController {

    @Autowired
    private PointService pointService;

    @RequestMapping(method = RequestMethod.GET, value = "/getCompositePointsData", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompositePointEvent getCompositePointsData(@RequestParam UUID pointEventID) {
        return Utils.returnOrException(pointService.getCompositePointEvent(pointEventID));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCompositePointEvents", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CompositePointEvent> getCompositePointEvents(@RequestParam UUID relID) {
        return pointService.getCompositePointEvents(relID);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/givePoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity givePoints(@RequestBody NewPointEvent newPointEvent) {
        return new ResponseEntity<>(Collections.singletonMap("success", pointService.givePoints(newPointEvent)), HttpStatus.OK);
    }
}