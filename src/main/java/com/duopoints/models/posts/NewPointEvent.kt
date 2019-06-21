package com.duopoints.models.posts

import com.duopoints.db.tables.pojos.Point
import com.duopoints.db.tables.pojos.PointEvent

import java.sql.Timestamp
import java.util.UUID

class NewPointEvent : PointEvent {

    var points: List<Point>? = null
    var mediaCount: Int = 0

    constructor(points: List<Point>, mediaCount: Int) {
        this.points = points
        this.mediaCount = mediaCount
    }

    constructor(value: PointEvent, points: List<Point>, mediaCount: Int) : super(value) {
        this.points = points
        this.mediaCount = mediaCount
    }

    constructor(pointEventUuid: UUID, pointGiverUserUuid: UUID, relationshipUuid: UUID, pointEventEmotionNumber: Short?, pointEventTitle: String, pointEventSubtitle: String, pointEventType: String, pointEventStatus: String, pointEventComment: String, pointEventLikes: Short?, createdUtc: Timestamp, lastModifiedUtc: Timestamp, points: List<Point>, mediaCount: Int) : super(pointEventUuid, pointGiverUserUuid, relationshipUuid, pointEventEmotionNumber, pointEventTitle, pointEventSubtitle, pointEventType, pointEventStatus, pointEventComment, pointEventLikes, createdUtc, lastModifiedUtc) {
        this.points = points
        this.mediaCount = mediaCount
    }
}