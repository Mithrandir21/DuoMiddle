package com.duopoints.service.fcm;

public class FcmData {

    public static final String NOTIFICATION_TYPE = "notification_type";

    // NOTIFICATION TYPES
    public static final String NEW_POINTS_TYPE = "new_points";
    public static final String NEW_LIKE_TYPE = "new_like";
    public static final String NEW_COMMENT_TYPE = "new_comment";
    public static final String NEW_FRIEND_REQUEST_TYPE = "new_friend_request";
    public static final String NEW_RELATIONSHIP_REQUEST_TYPE = "new_relationship_request";
    public static final String NEW_FOLLOWER_TYPE = "new_follower";
    public static final String NEW_RELATIONSHIP_TYPE = "new_relationship";


    // POINT EVENT fields
    public static final String POINT_EVENT_ID = "event_id";
    public static final String POINT_EVENT_TITLE = "event_title";
    public static final String POINT_SUM = "total_points";

    // FRIEND REQUEST fields
    public static final String FRIEND_REQUEST_ID = "friend_request_id";

    // RELATIONSHIP REQUEST fields
    public static final String RELATIONSHIP_REQUEST_ID = "friend_request_id";

    // NEW RELATIONSHIP fields
    public static final String NEW_RELATIONSHIP_ID = "new_relationship_id";
}