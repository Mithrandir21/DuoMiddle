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
    public static final String RELATIONSHIP_REQUEST_UPDATED_TYPE = "relationship_request_updated_type";
    public static final String RELATIONSHIP_BREAKUP_TYPE = "relationship_breakup_type";


    // POINT EVENT fields
    public static final String POINT_EVENT_ID = "event_id";
    public static final String POINT_EVENT_TITLE = "event_title";
    public static final String POINT_SUM = "total_points";

    // FRIEND REQUEST fields
    public static final String FRIEND_REQUEST_ID = "friend_request_id";
    public static final String FRIEND_REQUEST_SENDER_NAME = "friend_request_sender_name";

    // RELATIONSHIP REQUEST fields
    public static final String RELATIONSHIP_REQUEST_ID = "relationship_request_id";
    public static final String RELATIONSHIP_REQUEST_SENDER_NAME = "relationship_request_sender_name";

    // NEW RELATIONSHIP fields
    public static final String NEW_RELATIONSHIP_ID = "new_relationship_id";
    public static final String NEW_RELATIONSHIP_SENDER_NAME = "NEW_RELATIONSHIP_SENDER_NAME";

    // RELATIONSHIP BREAKUP fields
    public static final String BREAKUP_STATUS = "breakup_status";
}