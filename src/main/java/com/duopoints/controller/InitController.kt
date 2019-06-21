package com.duopoints.controller

import com.duopoints.service.InitService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import java.util.Collections

@Controller
@RequestMapping("/init/")
class InitController {

    @Autowired
    lateinit var initService: InitService

    @RequestMapping(method = [RequestMethod.GET], value = ["/populateLevelReqs"])
    fun populateLevelReq(): ResponseEntity<*> {
        initService.populateLevelReq()
        return ResponseEntity(Collections.singletonMap("success", true), HttpStatus.OK)
    }

    @RequestMapping(method = [RequestMethod.GET], value = ["/populatePointsInfo"])
    fun populatePointsInfo(): ResponseEntity<*> {
        initService.populatePointsInfo()
        return ResponseEntity(Collections.singletonMap("success", true), HttpStatus.OK)
    }
}