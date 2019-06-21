package com.duopoints.service

import com.duopoints.db.tables.pojos.MediaObject
import com.duopoints.db.tables.pojos.MediaObjectList
import org.jooq.Record1
import org.jooq.SelectConditionStep
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import javax.validation.constraints.NotNull
import java.util.UUID

import com.duopoints.db.tables.MediaObject.MEDIA_OBJECT
import com.duopoints.db.tables.MediaObjectList.MEDIA_OBJECT_LIST

@Service
class MediaService {

    @Autowired
    @Qualifier("dsl")
    lateinit var duo: DefaultDSLContext


    /**********************
     * MEDIA OBJECT
     **********************/

    fun getMediaObject(@NotNull mediaObjectID: UUID): MediaObject? =
            duo.selectFrom(MEDIA_OBJECT).where(MEDIA_OBJECT.MEDIA_OBJECT_UUID.eq(mediaObjectID)).fetchOneInto(MediaObject::class.java)

    @NotNull
    fun getPointEventMediaObjects(@NotNull pointEventID: UUID): List<MediaObject> {
        // Select all the MediaObject UUIDs in the MediaObjectList where the PointEvent is the given UUID.
        val whereInClause = duo.select(MEDIA_OBJECT_LIST.MEDIA_OBJECT_UUID).from(MEDIA_OBJECT_LIST).where(MEDIA_OBJECT_LIST.POINT_EVENT_UUID.eq(pointEventID))

        return duo.selectFrom(MEDIA_OBJECT).where(MEDIA_OBJECT.MEDIA_OBJECT_UUID.`in`(whereInClause)).fetchInto(MediaObject::class.java)
    }

    @NotNull
    fun createMediaObject(description: String?, @NotNull type: String): MediaObject =
            if (description == null) {
                duo.insertInto(MEDIA_OBJECT).columns(MEDIA_OBJECT.MEDIA_OBJECT_TYPE).values(type).returning().fetchOne().into(MediaObject::class.java)
            } else {
                duo.insertInto(MEDIA_OBJECT).columns(MEDIA_OBJECT.MEDIA_OBJECT_DESC, MEDIA_OBJECT.MEDIA_OBJECT_TYPE).values(description, type).returning().fetchOne().into(MediaObject::class.java)
            }


    /**********************
     * MEDIA OBJECT LIST
     **********************/

    fun getMediaObjectList(@NotNull mediaObjectListID: UUID): MediaObjectList? =
            duo.selectFrom(MEDIA_OBJECT_LIST).where(MEDIA_OBJECT_LIST.MEDIA_OBJECT_LIST_UUID.eq(mediaObjectListID)).fetchOneInto(MediaObjectList::class.java)

    @NotNull
    fun getPointEventMediaObjectList(@NotNull pointEventID: UUID): List<MediaObjectList> =
            duo.selectFrom(MEDIA_OBJECT_LIST).where(MEDIA_OBJECT_LIST.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(MediaObjectList::class.java)

    @NotNull
    fun createMediaObjectList(@NotNull pointEventID: UUID, pointID: UUID?, @NotNull mediaObjectID: UUID): MediaObjectList =
            if (pointID == null) {
                duo.insertInto(MEDIA_OBJECT_LIST).columns(MEDIA_OBJECT_LIST.POINT_EVENT_UUID, MEDIA_OBJECT_LIST.MEDIA_OBJECT_UUID)
                        .values(pointEventID, mediaObjectID).returning().fetchOne().into(MediaObjectList::class.java)
            } else {
                duo.insertInto(MEDIA_OBJECT_LIST).columns(MEDIA_OBJECT_LIST.POINT_EVENT_UUID, MEDIA_OBJECT_LIST.POINT_UUID, MEDIA_OBJECT_LIST.MEDIA_OBJECT_UUID)
                        .values(pointEventID, pointID, mediaObjectID).returning().fetchOne().into(MediaObjectList::class.java)
            }
}