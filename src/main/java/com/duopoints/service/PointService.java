package com.duopoints.service;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.PointEvent;
import com.duopoints.db.tables.pojos.PointEventComment;
import com.duopoints.db.tables.pojos.Pointdata;
import com.duopoints.db.tables.records.PointRecord;
import com.duopoints.models.composites.gets.PointEventData;
import com.duopoints.models.composites.posts.NewPointEvent;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.PointEvent.POINT_EVENT;
import static com.duopoints.db.tables.PointEventComment.POINT_EVENT_COMMENT;
import static com.duopoints.db.tables.Pointdata.POINTDATA;

@Service
public class PointService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;


    @Transactional
    public boolean givePoints(@NotNull final NewPointEvent combinedPointEvent) {
        // First we INSERT the PointEvent, so that the PointEventID exists for Points insertion
        PointEvent newPointEvent = duo.insertInto(POINT_EVENT)
                .columns(POINT_EVENT.POINT_GIVER_USER_UUID, POINT_EVENT.RELATIONSHIP_UUID, POINT_EVENT.POINT_EVENT_EMOTION_NUMBER,
                        POINT_EVENT.POINT_EVENT_TITLE, POINT_EVENT.POINT_EVENT_SUBTITLE,
                        POINT_EVENT.POINT_EVENT_TYPE, POINT_EVENT.POINT_EVENT_STATUS, POINT_EVENT.POINT_EVENT_COMMENT)
                .values(combinedPointEvent.getPointGiverUserUuid(), combinedPointEvent.getRelationshipUuid(), combinedPointEvent.getPointEventEmotionNumber(),
                        combinedPointEvent.getPointEventTitle(), combinedPointEvent.getPointEventSubtitle(),
                        combinedPointEvent.getPointEventType(), combinedPointEvent.getPointEventStatus(), combinedPointEvent.getPointEventComment())
                .returning()
                .fetchOne()
                .into(PointEvent.class);

        List<PointRecord> points = new ArrayList<>();

        short position = 0;
        // Now we go through each Point in the given list and create each Record for insertion
        for (Point singlePoint : combinedPointEvent.getPoints()) {
            points.add(new PointRecord()
                    .value2(newPointEvent.getPointEventUuid())
                    .value3(singlePoint.getPointValue())
                    .value4(singlePoint.getPointTypeNumber())
                    .value5(position)
                    .value6(singlePoint.getPointComment()));

            position++;
        }

        duo.batchInsert(points).execute();

        return true;
    }

    public PointEventData getPointEvent(@NotNull UUID pointEventID) {
        // First we get the PointEvent with the given ID
        PointEvent pointevent = duo.selectFrom(POINT_EVENT).where(POINT_EVENT.POINT_EVENT_UUID.eq(pointEventID)).fetchOneInto(PointEvent.class);

        // Now we get the list of Pointsdata for the specific PointEvent
        List<Pointdata> pointsdata = duo.selectFrom(POINTDATA).where(POINTDATA.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(Pointdata.class);

        // Not we get the list of PointEventComments
        List<PointEventComment> pointEventComments = duo.selectFrom(POINT_EVENT_COMMENT).where(POINT_EVENT_COMMENT.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(PointEventComment.class);

        return new PointEventData(pointevent, pointsdata, pointEventComments);
    }
}
