package com.duopoints.service;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.PointEvent;
import com.duopoints.db.tables.pojos.Pointdata;
import com.duopoints.db.tables.pojos.Pointeventcommentdata;
import com.duopoints.db.tables.records.PointRecord;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.composites.CompositePointEvent;
import com.duopoints.models.composites.CompositeRelationship;
import com.duopoints.models.posts.NewPointEvent;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.PointEvent.POINT_EVENT;
import static com.duopoints.db.tables.Pointdata.POINTDATA;
import static com.duopoints.db.tables.Pointeventcommentdata.POINTEVENTCOMMENTDATA;

@SuppressWarnings("WeakerAccess")
@Service
public class PointService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;

    @Autowired
    private RelationshipService relationshipService;


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

    @Nullable
    public PointEvent getPointEvent(@NotNull UUID pointEventID) {
        return duo.selectFrom(POINT_EVENT).where(POINT_EVENT.POINT_EVENT_UUID.eq(pointEventID)).fetchOneInto(PointEvent.class);
    }

    @NotNull
    public List<PointEvent> getPointEvents(@NotNull UUID relID) {
        return duo.selectFrom(POINT_EVENT).where(POINT_EVENT.RELATIONSHIP_UUID.eq(relID)).fetchInto(PointEvent.class);
    }

    @NotNull
    public List<PointEvent> getPointEventsGivenByUser(@NotNull UUID relID, @NotNull UUID givingUser) {
        return duo.selectFrom(POINT_EVENT)
                .where(POINT_EVENT.RELATIONSHIP_UUID.eq(relID))
                .and(POINT_EVENT.POINT_GIVER_USER_UUID.eq(givingUser))
                .fetchInto(PointEvent.class);
    }


    /*****************************
     * CompositePointEvent
     *****************************/

    @NotNull
    public CompositePointEvent getCompositePointEvent(@NotNull UUID pointEventID) {
        PointEvent pointEvent = getPointEvent(pointEventID);

        if (pointEvent != null) {
            return getCompositePointEvent(pointEvent);
        } else {
            throw new NoMatchingRowException("No PointEvent found for pointEventID='" + pointEventID + "'");
        }
    }

    public CompositePointEvent getCompositePointEvent(@NotNull PointEvent pointevent) {
        return getCompositePointEvent(pointevent, getPointsData(pointevent.getPointEventUuid()));
    }

    public CompositePointEvent getCompositePointEvent(@NotNull PointEvent pointevent, @NotNull CompositeRelationship compositeRelationship) {
        return getCompositePointEvent(pointevent, getPointsData(pointevent.getPointEventUuid()), getPointEventCommentdata(pointevent.getPointEventUuid()), compositeRelationship);
    }

    public CompositePointEvent getCompositePointEvent(@NotNull PointEvent pointevent, @NotNull List<Pointdata> pointsdata) {
        return getCompositePointEvent(pointevent, pointsdata, getPointEventCommentdata(pointevent.getPointEventUuid()));
    }

    @NotNull
    public CompositePointEvent getCompositePointEvent(@NotNull PointEvent pointevent, @NotNull List<Pointdata> pointsdata, @NotNull List<Pointeventcommentdata> pointEventComments) {
        CompositeRelationship compositeRelationship = relationshipService.getCompositeRelationship(pointevent.getRelationshipUuid());

        if (compositeRelationship != null) {
            return getCompositePointEvent(pointevent, pointsdata, pointEventComments, compositeRelationship);
        } else {
            throw new NoMatchingRowException("No CompositeRelationship found for relationshioID='" + pointevent.getRelationshipUuid() + "'");
        }
    }

    public CompositePointEvent getCompositePointEvent(@NotNull PointEvent pointevent, @NotNull List<Pointdata> pointsdata, @NotNull List<Pointeventcommentdata> pointEventComments, @NotNull CompositeRelationship compositeRelationship) {
        return new CompositePointEvent(pointevent, compositeRelationship, pointsdata, pointEventComments);
    }

    public List<CompositePointEvent> getCompositePointEvents(@NotNull UUID relID) {
        return getCompositePointEvents(relationshipService.getCompositeRelationship(relID));
    }

    public List<CompositePointEvent> getCompositePointEvents(@NotNull CompositeRelationship compositeRelationship) {
        List<CompositePointEvent> compositePointEvents = new ArrayList<>();

        // First get all the PointEvents of a Relationship
        List<PointEvent> relationshipPointEvents = getPointEvents(compositeRelationship.getRelationshipUuid());

        for (PointEvent singleEvent : relationshipPointEvents) {
            compositePointEvents.add(getCompositePointEvent(singleEvent, compositeRelationship));
        }

        return compositePointEvents;
    }

    public List<CompositePointEvent> getCompositePointEventsGivenByUserOne(@NotNull CompositeRelationship compositeRelationship) {
        return getCompositePointEventsGivenByUser(compositeRelationship, compositeRelationship.getUserUuid_1());
    }

    public List<CompositePointEvent> getCompositePointEventsGivenByUserTwo(@NotNull CompositeRelationship compositeRelationship) {
        return getCompositePointEventsGivenByUser(compositeRelationship, compositeRelationship.getUserUuid_2());
    }

    private List<CompositePointEvent> getCompositePointEventsGivenByUser(@NotNull CompositeRelationship compositeRelationship, @NotNull UUID userID) {
        List<CompositePointEvent> compositePointEvents = new ArrayList<>();

        // First get all the PointEvents of a Relationship
        List<PointEvent> relationshipPointEvents = getPointEventsGivenByUser(compositeRelationship.getRelationshipUuid(), userID);

        for (PointEvent singleEvent : relationshipPointEvents) {
            compositePointEvents.add(getCompositePointEvent(singleEvent, compositeRelationship));
        }

        return compositePointEvents;
    }


    /*******************
     * POINTDATA
     *******************/

    @NotNull
    public List<Pointdata> getPointsData(@NotNull UUID pointEventID) {
        return duo.selectFrom(POINTDATA).where(POINTDATA.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(Pointdata.class);
    }


    /******************************
     * POINTEVENTCOMMENTDATA
     ******************************/

    @NotNull
    public List<Pointeventcommentdata> getPointEventCommentdata(@NotNull UUID pointEventID) {
        return duo.selectFrom(POINTEVENTCOMMENTDATA).where(POINTEVENTCOMMENTDATA.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(Pointeventcommentdata.class);
    }
}
