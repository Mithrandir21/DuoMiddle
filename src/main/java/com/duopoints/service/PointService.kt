package com.duopoints.service

import com.duopoints.RequestParameters
import com.duopoints.db.tables.pojos.*
import com.duopoints.db.tables.records.PointRecord
import com.duopoints.errorhandling.NoMatchingRowException
import com.duopoints.models.composites.CompositePointEvent
import com.duopoints.models.composites.CompositeRelationship
import com.duopoints.models.posts.NewPointEvent
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.constraints.NotNull
import java.util.*

import com.duopoints.db.tables.PointEvent.POINT_EVENT
import com.duopoints.db.tables.PointEventCommentdata.POINT_EVENT_COMMENTDATA
import com.duopoints.db.tables.PointEventEmotion.POINT_EVENT_EMOTION
import com.duopoints.db.tables.PointEventLike.POINT_EVENT_LIKE
import com.duopoints.db.tables.PointType.POINT_TYPE
import com.duopoints.db.tables.PointTypeCategory.POINT_TYPE_CATEGORY
import com.duopoints.db.tables.Pointdata.POINTDATA
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class PointService {

    @Autowired
    @Qualifier("dsl")
    lateinit var duo: DefaultDSLContext

    @Autowired
    lateinit var relationshipService: RelationshipService

    @Autowired
    lateinit var mediaService: MediaService

    val allActivePointTypes: List<PointType>
        get() = duo.selectFrom(POINT_TYPE).where(POINT_TYPE.POINT_TYPE_STATUS.eq(RequestParameters.POINT_TYPE_status_active)).fetchInto(PointType::class.java)

    val allActivePointTypeCategories: List<PointTypeCategory>
        get() = duo.selectFrom(POINT_TYPE_CATEGORY).where(POINT_TYPE_CATEGORY.POINT_TYPE_CATEGORY_STATUS.eq(RequestParameters.POINT_TYPE_CATEGORY_status_active)).fetchInto(PointTypeCategory::class.java)


    /**************************
     * POINT TYPES EMOTIONS
     **************************/

    val allActivePointEventEmotions: List<PointEventEmotion>
        get() = duo.selectFrom(POINT_EVENT_EMOTION).where(POINT_EVENT_EMOTION.POINT_EVENT_EMOTION_STATUS.eq(RequestParameters.POINT_EVENT_EMOTION_status_active)).fetchInto(PointEventEmotion::class.java)


    @Transactional
    fun givePoints(combinedPointEvent: NewPointEvent): CompositePointEvent {
        // First we INSERT the PointEvent, so that the PointEventID exists for MediaObjectList and Points insertion
        val newPointEvent = duo.insertInto(POINT_EVENT)
                .columns(POINT_EVENT.POINT_GIVER_USER_UUID, POINT_EVENT.RELATIONSHIP_UUID, POINT_EVENT.POINT_EVENT_EMOTION_NUMBER,
                        POINT_EVENT.POINT_EVENT_TITLE, POINT_EVENT.POINT_EVENT_SUBTITLE,
                        POINT_EVENT.POINT_EVENT_TYPE, POINT_EVENT.POINT_EVENT_STATUS, POINT_EVENT.POINT_EVENT_COMMENT)
                .values(combinedPointEvent.pointGiverUserUuid, combinedPointEvent.relationshipUuid, combinedPointEvent.pointEventEmotionNumber,
                        combinedPointEvent.pointEventTitle, combinedPointEvent.pointEventSubtitle,
                        combinedPointEvent.pointEventType, combinedPointEvent.pointEventStatus, combinedPointEvent.pointEventComment)
                .returning()
                .fetchOne()
                .into(PointEvent::class.java)


        // Now we check if any MediaObjects need to be created (with MediaObjectList)
        if (combinedPointEvent.mediaCount > 0) {
            for (i in 1..combinedPointEvent.mediaCount) {
                val mediaObject = mediaService.createMediaObject("Image $i", "IMAGE")
                mediaService.createMediaObjectList(newPointEvent.pointEventUuid, null, mediaObject.mediaObjectUuid)
            }
        }


        val points = ArrayList<PointRecord>()

        var position: Short = 0
        // Now we go through each Point in the given list and create each Record for insertion
        combinedPointEvent.points?.forEach {
            points.add(PointRecord()
                    .value2(newPointEvent.pointEventUuid)
                    .value3(it.pointTypeNumber)
                    .value4(it.pointTypeCategoryNumber)
                    .value5(position)
                    .value6(it.pointComment))

            position++
        }

        duo.batchInsert(points).execute()

        return getCompositePointEvent(newPointEvent.pointEventUuid)
    }

    fun getPointEvent(pointEventID: UUID): PointEvent? =
            duo.selectFrom(POINT_EVENT).where(POINT_EVENT.POINT_EVENT_UUID.eq(pointEventID)).fetchOneInto(PointEvent::class.java)


    fun getPointEvents(relID: UUID): List<PointEvent> =
            duo.selectFrom(POINT_EVENT).where(POINT_EVENT.RELATIONSHIP_UUID.eq(relID)).fetchInto(PointEvent::class.java)


    fun getPointEventsGivenByUser(relID: UUID, givingUser: UUID): List<PointEvent> =
            duo.selectFrom(POINT_EVENT)
                    .where(POINT_EVENT.RELATIONSHIP_UUID.eq(relID))
                    .and(POINT_EVENT.POINT_GIVER_USER_UUID.eq(givingUser))
                    .fetchInto(PointEvent::class.java)


    /*****************************
     * CompositePointEvent
     *****************************/

    fun getCompositePointEvent(pointEventID: UUID): CompositePointEvent {
        val pointEvent = getPointEvent(pointEventID)

        return pointEvent?.let { getCompositePointEvent(it) } ?: throw NoMatchingRowException("No PointEvent found for pointEventID='$pointEventID'")
    }

    fun getCompositePointEvent(pointevent: PointEvent, compositeRelationship: CompositeRelationship): CompositePointEvent =
            getCompositePointEvent(pointevent, getPointsData(pointevent.pointEventUuid), getPointEventCommentdata(pointevent.pointEventUuid), compositeRelationship)

    @JvmOverloads
    fun getCompositePointEvent(pointevent: PointEvent, pointsdata: List<Pointdata> = getPointsData(pointevent.pointEventUuid)): CompositePointEvent =
            getCompositePointEvent(pointevent, pointsdata, getPointEventCommentdata(pointevent.pointEventUuid))


    fun getCompositePointEvent(pointevent: PointEvent, pointsdata: List<Pointdata>, pointEventComments: List<PointEventCommentdata>): CompositePointEvent {
        val compositeRelationship = relationshipService.getCompositeRelationship(pointevent.relationshipUuid)
        return getCompositePointEvent(pointevent, pointsdata, pointEventComments, compositeRelationship)
    }

    fun getCompositePointEvent(pointevent: PointEvent, pointsdata: List<Pointdata>, pointEventComments: List<PointEventCommentdata>, compositeRelationship: CompositeRelationship): CompositePointEvent =
            CompositePointEvent(pointevent, compositeRelationship, pointsdata, pointEventComments, mediaService.getPointEventMediaObjects(pointevent.pointEventUuid))

    fun getCompositePointEvents(relID: UUID): List<CompositePointEvent> = getCompositePointEvents(relationshipService.getCompositeRelationship(relID))

    fun getCompositePointEvents(userIDs: List<UUID>): List<CompositePointEvent> {
        // Set which insert sorted based on Creation timestamp of CompositePointEvent
        val totalEvents = TreeSet(Comparator.comparing<CompositePointEvent, LocalDateTime> { it.createdUtc })

        // First get the Users Relationships
        val relationships = relationshipService.getActiveUsersRelationship(userIDs)

        // For each Rel, get the CompositeRelationship and then all CompositePointEvents. Add sorted to Set.
        for (rel in relationships) {
            totalEvents.addAll(getCompositePointEvents(relationshipService.getCompositeRelationship(rel)))
        }

        return ArrayList(totalEvents)
    }

    fun getCompositePointEvents(compositeRelationship: CompositeRelationship): List<CompositePointEvent> {
        val compositePointEvents = ArrayList<CompositePointEvent>()

        // First get all the PointEvents of a Relationship
        val relationshipPointEvents = getPointEvents(compositeRelationship.relationshipUuid)

        for (singleEvent in relationshipPointEvents) {
            compositePointEvents.add(getCompositePointEvent(singleEvent, compositeRelationship))
        }

        return compositePointEvents
    }

    fun getCompositePointEventsGivenByUserOne(compositeRelationship: CompositeRelationship): List<CompositePointEvent> =
            getCompositePointEventsGivenByUser(compositeRelationship, compositeRelationship.userUuid_1)

    fun getCompositePointEventsGivenByUserTwo(compositeRelationship: CompositeRelationship): List<CompositePointEvent> =
            getCompositePointEventsGivenByUser(compositeRelationship, compositeRelationship.userUuid_2)

    private fun getCompositePointEventsGivenByUser(compositeRelationship: CompositeRelationship, userID: UUID): List<CompositePointEvent> {
        val compositePointEvents = ArrayList<CompositePointEvent>()

        // First get all the PointEvents of a Relationship
        val relationshipPointEvents = getPointEventsGivenByUser(compositeRelationship.relationshipUuid, userID)

        for (singleEvent in relationshipPointEvents) {
            compositePointEvents.add(getCompositePointEvent(singleEvent, compositeRelationship))
        }

        return compositePointEvents
    }


    /*******************
     * POINT TYPES
     *******************/

    fun getActivePointType(pointTypeID: Short): PointType? =
            duo.selectFrom(POINT_TYPE)
                    .where(POINT_TYPE.POINT_TYPE_NUMBER.eq(pointTypeID))
                    .and(POINT_TYPE.POINT_TYPE_STATUS.eq(RequestParameters.POINT_TYPE_status_active))
                    .fetchOneInto(PointType::class.java)


    fun searchForActivePointTypes(query: String): List<PointType> =
            duo.selectFrom(POINT_TYPE)
                    .where(POINT_TYPE.POINT_TYPE_TITLE.like("%$query%"))
                    .or(POINT_TYPE.POINT_TYPE_DESCRIPTION.like("%$query%"))
                    .and(POINT_TYPE.POINT_TYPE_STATUS.eq(RequestParameters.POINT_TYPE_status_active))
                    .fetchInto(PointType::class.java)

    /**************************
     * POINT TYPES CATEGORIES
     **************************/

    fun getActivePointTypeCategory(pointTypeCategoryID: Short): PointTypeCategory =
            duo.selectFrom(POINT_TYPE_CATEGORY)
                    .where(POINT_TYPE_CATEGORY.POINT_TYPE_CATEGORY_NUMBER.eq(pointTypeCategoryID))
                    .and(POINT_TYPE_CATEGORY.POINT_TYPE_CATEGORY_STATUS.eq(RequestParameters.POINT_TYPE_CATEGORY_status_active))
                    .fetchOneInto(PointTypeCategory::class.java)


    /*********************
     * POINT EVENT LIKES
     *********************/

    fun likedPointEvents(userID: UUID): List<UUID> =
            duo.select(POINT_EVENT_LIKE.POINT_EVENT_UUID)
                    .from(POINT_EVENT_LIKE)
                    .where(POINT_EVENT_LIKE.POINT_EVENT_LIKE_USER_UUID.eq(userID))
                    .fetchInto(UUID::class.java)

    fun likeEvent(pointID: UUID, userID: UUID): PointEventLike =
            duo.insertInto(POINT_EVENT_LIKE)
                    .columns(POINT_EVENT_LIKE.POINT_EVENT_UUID, POINT_EVENT_LIKE.POINT_EVENT_LIKE_USER_UUID)
                    .values(pointID, userID)
                    .returning()
                    .fetchOne()
                    .into(PointEventLike::class.java)

    fun unlikeEvent(eventID: UUID, userID: UUID): PointEventLike =
            duo.deleteFrom(POINT_EVENT_LIKE)
                    .where(POINT_EVENT_LIKE.POINT_EVENT_UUID.eq(eventID))
                    .and(POINT_EVENT_LIKE.POINT_EVENT_LIKE_USER_UUID.eq(userID))
                    .returning()
                    .fetchOne()
                    .into(PointEventLike::class.java)


    /*******************
     * POINTDATA
     *******************/

    fun getPointsData(pointEventID: UUID): List<Pointdata> =
            duo.selectFrom(POINTDATA).where(POINTDATA.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(Pointdata::class.java)


    /******************************
     * POINTEVENTCOMMENTDATA
     ******************************/

    fun getPointEventCommentdata(pointEventID: UUID): List<PointEventCommentdata> =
            duo.selectFrom(POINT_EVENT_COMMENTDATA).where(POINT_EVENT_COMMENTDATA.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(PointEventCommentdata::class.java)
}
