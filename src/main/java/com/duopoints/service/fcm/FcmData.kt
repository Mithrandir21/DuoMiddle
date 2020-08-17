package com.duopoints.service.fcm

object FcmData {
    const val NOTIFICATION_TYPE = "notification_type"

    // NOTIFICATION TYPES
    const val NEW_POINTS_TYPE = "new_points"
    const val NEW_LIKE_TYPE = "new_like"
    const val NEW_COMMENT_TYPE = "new_comment"
    const val NEW_FRIEND_REQUEST_TYPE = "new_friend_request"
    const val NEW_RELATIONSHIP_REQUEST_TYPE = "new_relationship_request"
    const val NEW_FOLLOWER_TYPE = "new_follower"
    const val NEW_RELATIONSHIP_TYPE = "new_relationship"
    const val RELATIONSHIP_REQUEST_UPDATED_TYPE = "relationship_request_updated_type"
    const val RELATIONSHIP_BREAKUP_TYPE = "relationship_breakup_type"

    // POINT EVENT fields
    const val POINT_EVENT_ID = "event_id"
    const val POINT_EVENT_TITLE = "event_title"
    const val POINT_SUM = "total_points"

    // FRIEND REQUEST fields
    const val FRIEND_REQUEST_ID = "friend_request_id"
    const val FRIEND_REQUEST_SENDER_NAME = "friend_request_sender_name"

    // RELATIONSHIP REQUEST fields
    const val RELATIONSHIP_REQUEST_ID = "relationship_request_id"
    const val RELATIONSHIP_REQUEST_SENDER_NAME = "relationship_request_sender_name"

    // NEW RELATIONSHIP fields
    const val NEW_RELATIONSHIP_ID = "new_relationship_id"
    const val NEW_RELATIONSHIP_SENDER_NAME = "NEW_RELATIONSHIP_SENDER_NAME"

    // RELATIONSHIP BREAKUP fields
    const val BREAKUP_STATUS = "breakup_status"
}