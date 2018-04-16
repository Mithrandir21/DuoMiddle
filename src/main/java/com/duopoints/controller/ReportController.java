package com.duopoints.controller;

import com.duopoints.Utils;
import com.duopoints.db.tables.pojos.ReportedContent;
import com.duopoints.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(method = RequestMethod.POST, value = "/reportContent", produces = MediaType.APPLICATION_JSON_VALUE)
    public ReportedContent reportContent(@RequestBody ReportedContent reportedContent) {
        return Utils.returnOrException(reportService.reportContent(reportedContent));
    }
}
