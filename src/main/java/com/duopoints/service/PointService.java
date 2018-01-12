package com.duopoints.service;

import com.duopoints.db.tables.pojos.Point;
import com.duopoints.db.tables.pojos.Pointdata;
import com.duopoints.db.tables.pojos.Pointevent;
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

import static com.duopoints.db.tables.Pointdata.POINTDATA;
import static com.duopoints.db.tables.Pointevent.POINTEVENT;

@Service
public class PointService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;


    @Transactional
    public boolean givePoints(@NotNull final NewPointEvent combinedPointEvent) {
        // First we INSERT the PointEvent, so that the PointEventID exists for Points insertion
        Pointevent newPointEvent = duo.insertInto(POINTEVENT)
                .columns(POINTEVENT.POINTGIVERUSERDB_ID, POINTEVENT.RELATIONSHIPDB_ID, POINTEVENT.POINTEVENTEMOTION_NUMBER,
                        POINTEVENT.POINTEVENTTYPE, POINTEVENT.POINTEVENTSTATUS, POINTEVENT.POINTEVENT_COMMENT)
                .values(combinedPointEvent.getPointgiveruserdbId(), combinedPointEvent.getRelationshipdbId(), combinedPointEvent.getPointeventemotionNumber(),
                        combinedPointEvent.getPointeventtype(), combinedPointEvent.getPointeventstatus(), combinedPointEvent.getPointeventComment())
                .returning()
                .fetchOne()
                .into(Pointevent.class);

        List<PointRecord> points = new ArrayList<>();

        short position = 0;
        // Now we go through each Point in the given list and create each Record for insertion
        for (Point singlePoint : combinedPointEvent.getPoints()) {
            points.add(new PointRecord()
                    .value2(newPointEvent.getPointeventdbId())
                    .value3(singlePoint.getPointValue())
                    .value4(singlePoint.getPointtypeNumber())
                    .value5(position)
                    .value6(singlePoint.getPointComment()));

            position++;
        }

        duo.batchInsert(points).execute();

        return true;
    }

    public PointEventData getPointEvent(@NotNull UUID pointEventID) {
        // First we get the PointEvent with the given ID
        Pointevent pointevent = duo.selectFrom(POINTEVENT).where(POINTEVENT.POINTEVENTDB_ID.eq(pointEventID)).fetchOneInto(Pointevent.class);

        // Now we get the list of Pointsdata for the specific PointEvent
        List<Pointdata> pointsdata = duo.selectFrom(POINTDATA).where(POINTDATA.POINTEVENTDB_ID.eq(pointEventID)).fetchInto(Pointdata.class);

        return new PointEventData(pointevent, pointsdata);
    }
}
