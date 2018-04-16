package com.duopoints.service;


import com.duopoints.db.tables.pojos.ReportedContent;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static com.duopoints.db.tables.ReportedContent.REPORTED_CONTENT;

@Service
public class ReportService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;


    @NotNull
    public ReportedContent reportContent(ReportedContent reportedContent) {
        return duo.insertInto(REPORTED_CONTENT)
                .columns(REPORTED_CONTENT.REPORTED_CONTENT_CONTENT_UUID,
                        REPORTED_CONTENT.REPORTED_CONTENT_TYPE,
                        REPORTED_CONTENT.REPORTED_CONTENT_REASON,
                        REPORTED_CONTENT.REPORTED_USER_UUID)
                .values(reportedContent.getReportedContentContentUuid(),
                        reportedContent.getReportedContentType(),
                        reportedContent.getReportedContentReason(),
                        reportedContent.getReportedUserUuid())
                .returning()
                .fetchOne()
                .into(ReportedContent.class);
    }
}
