package com.duopoints.service;

import com.duopoints.db.tables.pojos.MediaObject;
import com.duopoints.db.tables.pojos.MediaObjectList;
import org.jooq.Record1;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.MediaObject.MEDIA_OBJECT;
import static com.duopoints.db.tables.MediaObjectList.MEDIA_OBJECT_LIST;

@Service
public class MediaService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;


    /**********************
     * MEDIA OBJECT
     **********************/

    @Nullable
    public MediaObject getMediaObject(@NotNull UUID mediaObjectID) {
        return duo.selectFrom(MEDIA_OBJECT).where(MEDIA_OBJECT.MEDIA_OBJECT_UUID.eq(mediaObjectID)).fetchOneInto(MediaObject.class);
    }

    @NotNull
    public List<MediaObject> getPointEventMediaObjects(@NotNull UUID pointEventID) {
        // Select all the MediaObject UUIDs in the MediaObjectList where the PointEvent is the given UUID.
        SelectConditionStep<Record1<UUID>> whereInClause = duo.select(MEDIA_OBJECT_LIST.MEDIA_OBJECT_UUID).from(MEDIA_OBJECT_LIST).where(MEDIA_OBJECT_LIST.POINT_EVENT_UUID.eq(pointEventID));

        return duo.selectFrom(MEDIA_OBJECT).where(MEDIA_OBJECT.MEDIA_OBJECT_UUID.in(whereInClause)).fetchInto(MediaObject.class);
    }

    @NotNull
    public MediaObject createMediaObject(@Nullable String description, @NotNull String type) {
        if (description == null) {
            return duo.insertInto(MEDIA_OBJECT).columns(MEDIA_OBJECT.MEDIA_OBJECT_TYPE).values(type).returning().fetchOne().into(MediaObject.class);
        } else {
            return duo.insertInto(MEDIA_OBJECT).columns(MEDIA_OBJECT.MEDIA_OBJECT_DESC, MEDIA_OBJECT.MEDIA_OBJECT_TYPE).values(description, type).returning().fetchOne().into(MediaObject.class);
        }
    }


    /**********************
     * MEDIA OBJECT LIST
     **********************/

    @Nullable
    public MediaObjectList getMediaObjectList(@NotNull UUID mediaObjectListID) {
        return duo.selectFrom(MEDIA_OBJECT_LIST).where(MEDIA_OBJECT_LIST.MEDIA_OBJECT_LIST_UUID.eq(mediaObjectListID)).fetchOneInto(MediaObjectList.class);
    }

    @NotNull
    public List<MediaObjectList> getPointEventMediaObjectList(@NotNull UUID pointEventID) {
        return duo.selectFrom(MEDIA_OBJECT_LIST).where(MEDIA_OBJECT_LIST.POINT_EVENT_UUID.eq(pointEventID)).fetchInto(MediaObjectList.class);
    }

    @NotNull
    public MediaObjectList createMediaObjectList(@NotNull UUID pointEventID, @Nullable UUID pointID, @NotNull UUID mediaObjectID) {
        if (pointID == null) {
            return duo.insertInto(MEDIA_OBJECT_LIST).columns(MEDIA_OBJECT_LIST.POINT_EVENT_UUID, MEDIA_OBJECT_LIST.MEDIA_OBJECT_UUID)
                    .values(pointEventID, mediaObjectID).returning().fetchOne().into(MediaObjectList.class);
        } else {
            return duo.insertInto(MEDIA_OBJECT_LIST).columns(MEDIA_OBJECT_LIST.POINT_EVENT_UUID, MEDIA_OBJECT_LIST.POINT_UUID, MEDIA_OBJECT_LIST.MEDIA_OBJECT_UUID)
                    .values(pointEventID, pointID, mediaObjectID).returning().fetchOne().into(MediaObjectList.class);
        }
    }
}