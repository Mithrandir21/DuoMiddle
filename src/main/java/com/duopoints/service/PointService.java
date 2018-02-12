package com.duopoints.service;

import com.duopoints.RequestParameters;
import com.duopoints.db.tables.pojos.*;
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
import java.util.*;

import static com.duopoints.db.tables.PointEvent.POINT_EVENT;
import static com.duopoints.db.tables.PointEventEmotion.POINT_EVENT_EMOTION;
import static com.duopoints.db.tables.PointType.POINT_TYPE;
import static com.duopoints.db.tables.PointTypeCategory.POINT_TYPE_CATEGORY;
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

    @Autowired
    private MediaService mediaService;


    @Transactional
    public CompositePointEvent givePoints(@NotNull final NewPointEvent combinedPointEvent) {
        // First we INSERT the PointEvent, so that the PointEventID exists for MediaObjectList and Points insertion
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


        // Now we check if any MediaObjects need to be created (with MediaObjectList)
        if (combinedPointEvent.getMediaCount() > 0) {
            for (int i = 1; i <= combinedPointEvent.getMediaCount(); i++) {
                MediaObject mediaObject = mediaService.createMediaObject("Image " + i, "IMAGE");
                mediaService.createMediaObjectList(newPointEvent.getPointEventUuid(), null, mediaObject.getMediaObjectUuid());
            }
        }


        List<PointRecord> points = new ArrayList<>();

        short position = 0;
        // Now we go through each Point in the given list and create each Record for insertion
        for (Point singlePoint : combinedPointEvent.getPoints()) {
            points.add(new PointRecord()
                    .value2(newPointEvent.getPointEventUuid())
                    .value3(singlePoint.getPointTypeNumber())
                    .value4(singlePoint.getPointTypeCategoryNumber())
                    .value5(position)
                    .value6(singlePoint.getPointComment()));

            position++;
        }

        duo.batchInsert(points).execute();

        return getCompositePointEvent(newPointEvent.getPointEventUuid());
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
        return new CompositePointEvent(pointevent, compositeRelationship, pointsdata, pointEventComments, mediaService.getPointEventMediaObjects(pointevent.getPointEventUuid()));
    }

    public List<CompositePointEvent> getCompositePointEvents(@NotNull UUID relID) {
        return getCompositePointEvents(relationshipService.getCompositeRelationship(relID));
    }

    public List<CompositePointEvent> getCompositePointEvents(@NotNull List<UUID> userIDs) {
        // Set which insert sorted based on Creation timestamp of CompositePointEvent
        Set<CompositePointEvent> totalEvents = new TreeSet<>(Comparator.comparing(PointEvent::getCreatedUtc));

        // First get the Users Relationships
        List<Relationship> relationships = relationshipService.getActiveUsersRelationship(userIDs);

        // For each Rel, get the CompositeRelationship and then all CompositePointEvents. Add sorted to Set.
        for (Relationship rel : relationships) {
            totalEvents.addAll(getCompositePointEvents(relationshipService.getCompositeRelationship(rel)));
        }

        // Reu
        return new ArrayList<>(totalEvents);
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
     * POINT TYPES
     *******************/

    @Nullable
    public PointType getActivePointType(short pointTypeID) {
        return duo.selectFrom(POINT_TYPE)
                .where(POINT_TYPE.POINT_TYPE_NUMBER.eq(pointTypeID))
                .and(POINT_TYPE.POINT_TYPE_STATUS.eq(RequestParameters.POINT_TYPE_status_active))
                .fetchOneInto(PointType.class);
    }

    @NotNull
    public List<PointType> getAllActivePointTypes() {
        return duo.selectFrom(POINT_TYPE).where(POINT_TYPE.POINT_TYPE_STATUS.eq(RequestParameters.POINT_TYPE_status_active)).fetchInto(PointType.class);
    }

    @NotNull
    public List<PointType> searchForActivePointTypes(@NotNull String query) {
        return duo.selectFrom(POINT_TYPE)
                .where(POINT_TYPE.POINT_TYPE_TITLE.like("%" + query + "%"))
                .or(POINT_TYPE.POINT_TYPE_DESCRIPTION.like("%" + query + "%"))
                .and(POINT_TYPE.POINT_TYPE_STATUS.eq(RequestParameters.POINT_TYPE_status_active))
                .fetchInto(PointType.class);
    }

    /**************************
     * POINT TYPES CATEGORIES
     **************************/

    public PointTypeCategory getActivePointTypeCategory(short pointTypeCategoryID) {
        return duo.selectFrom(POINT_TYPE_CATEGORY)
                .where(POINT_TYPE_CATEGORY.POINT_TYPE_CATEGORY_NUMBER.eq(pointTypeCategoryID))
                .and(POINT_TYPE_CATEGORY.POINT_TYPE_CATEGORY_STATUS.eq(RequestParameters.POINT_TYPE_CATEGORY_status_active))
                .fetchOneInto(PointTypeCategory.class);
    }

    @NotNull
    public List<PointTypeCategory> getAllActivePointTypeCategories() {
        return duo.selectFrom(POINT_TYPE_CATEGORY).where(POINT_TYPE_CATEGORY.POINT_TYPE_CATEGORY_STATUS.eq(RequestParameters.POINT_TYPE_CATEGORY_status_active)).fetchInto(PointTypeCategory.class);
    }

    /**************************
     * POINT TYPES EMOTIONS
     **************************/

    @NotNull
    public List<PointEventEmotion> getAllActivePointEventEmotions() {
        return duo.selectFrom(POINT_EVENT_EMOTION).where(POINT_EVENT_EMOTION.POINT_EVENT_EMOTION_STATUS.eq(RequestParameters.POINT_EVENT_EMOTION_status_active)).fetchInto(PointEventEmotion.class);
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
