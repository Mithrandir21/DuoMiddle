package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.MediaObject
import com.duopoints.db.tables.pojos.PointEvent
import com.duopoints.db.tables.pojos.PointEventCommentdata
import com.duopoints.db.tables.pojos.Pointdata

import javax.validation.constraints.NotNull

class CompositePointEvent(
        val value: PointEvent,
        val relationship: CompositeRelationship,
        val pointdata: List<Pointdata>,
        val pointEventComments: List<PointEventCommentdata>,
        val mediaObjects: List<MediaObject>
) : PointEvent(value) {

    var pointEventTotalPoints = 0

    init {
        pointdata.forEach { point ->
            pointEventTotalPoints += point.pointValue.toInt()
        }
    }
}