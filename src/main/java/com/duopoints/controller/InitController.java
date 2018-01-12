package com.duopoints.controller;

import com.duopoints.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

@Controller
@RequestMapping("/init/")
public class InitController {

    @Autowired
    private InitService initService;

    @RequestMapping(method = RequestMethod.GET, value = "/populateLevelReqs")
    public ResponseEntity populateLevelReq() {
        initService.populateLevelReq();
        return new ResponseEntity<>(Collections.singletonMap("success", true), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/populatePointsInfo")
    public ResponseEntity populatePointsInfo() {
        initService.populatePointsInfo();
        return new ResponseEntity<>(Collections.singletonMap("success", true), HttpStatus.OK);
    }
}