package com.duopoints;

public abstract class RequestParameters {
    public static final int CALLER_UNNECESSARY = -1;

    public static final String DELIMITER = "/";
    public static final String PROCEDURE_SEGMENT = "rpc";

    public static final String EXPLORE = "explore";
    public static final String SEARCH = "search";
    public static final String SEARCH_QUERY = "q";
    public static final String SESSIONS = "sessions";
    public static final String AUTHENTICATE = "authenticate";
    public static final String REGISTER = "register";
    public static final String FIRST_USE = "first_use";
    public static final String USERS = "users";
    public static final String PING = "ping";
    public static final String LOVE = "love";
    public static final String UNLOVE = "unlove";
    public static final String HATE = "hate";
    public static final String INFO = "info";
    public static final String SELECT = "select";

    /*********************
     * HEADER PARAMETERS
     *********************/

    public static final String PREFER = "Prefer";
    public static final String REPRESENTATION = "return=representation";

    /*********************
     * USERDATA PARAMETERS
     *********************/

    public static final String USERDATA_SEGMENT = "userdata";


    /*********************
     * BLOCKED USERS
     *********************/

    public static final String BLOCKED_USERS_SEGMENT_NAME = "blocked_users";
    public static final String BLOCKED_USERS_blocking_user_uuid = "blocking_user_uuid";
    public static final String BLOCKED_USERS_blocked_user_uuid = "blocked_user_uuid";


    /*********************
     * FRIEND
     *********************/

    public static final String FRIEND_SEGMENT_NAME = "friend";
    public static final String FRIEND_friend_uuid = "friend_uuid";
    public static final String FRIEND_user_uuid = "user_uuid";
    public static final String FRIEND_friends_list_uuid = "friends_list_uuid";
    public static final String FRIEND_friend_rights_uuid = "friend_rights_uuid";
    public static final String FRIEND_friendship_status = "friendship_status";

    public static final String FRIEND_friendship_status_active = "ACTIVE";
    public static final String FRIEND_friendship_status_ended = "ENDED";


    /*********************
     * FRIEND REQUEST
     *********************/

    public static final String FRIEND_REQUEST_SEGMENT_NAME = "friend_request";
    public static final String FRIEND_REQUEST_friend_request_uuid = "friend_request_uuid";
    public static final String FRIEND_REQUEST_friend_request_sender_user_uuid = "friend_request_sender_user_uuid";
    public static final String FRIEND_REQUEST_friend_request_recipient_user_uuid = "friend_request_recipient_user_uuid";
    public static final String FRIEND_REQUEST_friend_request_comment = "friend_request_comment";
    public static final String FRIEND_REQUEST_friend_request_status = "friend_request_status";

    public static final String FRIEND_REQUEST_friend_request_status_sent = "SENT";
    public static final String FRIEND_REQUEST_friend_request_status_waiting_for_recipient = "WAITING_FOR_RECIPIENT";
    public static final String FRIEND_REQUEST_friend_request_status_completed = "COMPLETED";
    public static final String FRIEND_REQUEST_friend_request_status_rejected = "REJECTED";
    public static final String FRIEND_REQUEST_friend_request_status_cancelled = "CANCELLED";
    public static final String FRIEND_REQUEST_friend_request_status_inactive = "INACTIVE";


    /*********************
     * FRIEND RIGHTS
     *********************/

    public static final String FRIEND_RIGHTS_SEGMENT_NAME = "friend_rights";
    public static final String FRIEND_RIGHTS_friend_rights_uuid = "friend_rights_uuid";
    public static final String FRIEND_RIGHTS_friend_can_see_relationship = "friend_can_see_relationship";
    public static final String FRIEND_RIGHTS_friend_can_see_timeline = "friend_can_see_timeline";
    public static final String FRIEND_RIGHTS_friend_can_see_history = "friend_can_see_history";


    /*********************
     * LEVEL POINT REQUIREMENTS
     *********************/

    public static final String LEVEL_POINT_REQUIREMENTS_SEGMENT_NAME = "LEVEL_POINT_REQUIREMENTS";
    public static final String LEVEL_POINT_REQUIREMENTS_level_number = "level_number";
    public static final String LEVEL_POINT_REQUIREMENTS_level_point_requirement = "level_point_requirement";


    /*********************
     * MEDIA OBJECT
     *********************/

    public static final String MEDIA_OBJECT_SEGMENT_NAME = "media_object";
    public static final String MEDIA_OBJECT_media_object_uuid = "media_object_uuid";
    public static final String MEDIA_OBJECT_media_object_desc = "media_object_desc";
    public static final String MEDIA_OBJECT_media_object_type = "media_object_type";


    /*********************
     * POINT
     *********************/

    public static final String POINT_SEGMENT_NAME = "point";
    public static final String POINT_uuid = "point_uuid";
    public static final String POINT_event_uuid = "point_event_uuid";
    public static final String POINT_value = "point_value";
    public static final String POINT_type_number = "point_type_number";
    public static final String POINT_chain_position = "point_chain_position";
    public static final String POINT_comment = "point_comment";


    /*********************
     * POINT EVENT
     *********************/

    public static final String POINT_EVENT_SEGMENT_NAME = "point_event";
    public static final String POINT_EVENT_point_event_uuid = "point_event_uuid";
    public static final String POINT_EVENT_point_giver_user_uuid = "point_giver_user_uuid";
    public static final String POINT_EVENT_relationship_uuid = "relationship_uuid";
    public static final String POINT_EVENT_point_event_emotion_number = "point_event_emotion_number";
    public static final String POINT_EVENT_point_event_timestamp = "point_event_timestamp";
    public static final String POINT_EVENT_point_event_timezone = "point_event_timezone";
    public static final String POINT_EVENT_point_event_type = "point_event_type";
    public static final String POINT_EVENT_point_event_status = "point_event_status";
    public static final String POINT_EVENT_point_event_status_comment = "point_event_status_comment";
    public static final String POINT_EVENT_point_event_title = "point_event_title";
    public static final String POINT_EVENT_point_event_comment = "point_event_comment";


    /*********************
     * POINT EVENT EMOTION
     *********************/

    public static final String POINT_EVENT_EMOTION_SEGMENT_NAME = "point_event_emotion";
    public static final String POINT_EVENT_EMOTION_point_event_emotion_number = "point_event_emotion_number";
    public static final String POINT_EVENT_EMOTION_point_event_emotion_title = "point_event_emotion_title";
    public static final String POINT_EVENT_EMOTION_point_event_emotion_description = "point_event_emotion_description";


    /*********************
     * POINT TYPE
     *********************/

    public static final String POINT_TYPE_SEGMENT_NAME = "point_type";
    public static final String POINT_TYPE_point_type_number = "point_type_number";
    public static final String POINT_TYPE_point_type_category_number = "point_type_category_number";
    public static final String POINT_TYPE_point_type_title = "point_type_title";
    public static final String POINT_TYPE_point_type_description = "point_type_description";


    /*********************
     * POINT TYPE CATEGORY
     *********************/

    public static final String POINT_TYPE_CATEGORY_SEGMENT_NAME = "point_type_category";
    public static final String POINT_TYPE_CATEGORY_point_type_category_number = "point_type_category_number";
    public static final String POINT_TYPE_CATEGORY_point_type_category_title = "point_type_category_title";
    public static final String POINT_TYPE_CATEGORY_point_type_category_description = "point_type_category_description";


    /*********************
     * REL BREAKUP REQUEST
     *********************/

    public static final String REL_BREAKUP_REQUEST_SEGMENT_NAME = "relationship_breakup_request";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_uuid = "relationship_breakup_requestdb_id";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_requesting_user_uuid = "relationship_breakup_requesting_user_uuid";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_relationship_uuid = "relationship_breakup_request_relationship_uuid";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_comment = "relationship_breakup_request_comment";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_status = "relationship_breakup_request_status";

    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_status_completed = "COMPLETED";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_status_processing = "PROCESSING";
    public static final String REL_BREAKUP_REQUEST_rel_breakup_request_status_cancelled = "CANCELLED";


    /*********************
     * RELATIONSHIP
     *********************/

    public static final String RELATIONSHIP_SEGMENT_NAME = "relationship";
    public static final String RELATIONSHIP_relationship_uuid = "relationship_uuid";
    public static final String RELATIONSHIP_user_uuid_1 = "user_uuid_1";
    public static final String RELATIONSHIP_user_uuid_2 = "user_uuid_2";
    public static final String RELATIONSHIP_rel_ach_list_uuid = "rel_ach_list_uuid";
    public static final String RELATIONSHIP_start_date = "start_date";
    public static final String RELATIONSHIP_end_date = "end_date";
    public static final String RELATIONSHIP_auto_accept_points = "auto_accept_points";
    public static final String RELATIONSHIP_relationship_image_uuid = "relationship_image_uuid";
    public static final String RELATIONSHIP_status = "status";
    public static final String RELATIONSHIP_is_secret = "is_secret";
    public static final String RELATIONSHIP_revivable = "revivable";
    public static final String RELATIONSHIP_rel_title = "rel_title";
    public static final String RELATIONSHIP_rel_desc = "rel_desc";
    public static final String RELATIONSHIP_rel_total_points = "rel_total_points";

    public static final String RELATIONSHIP_status_dating = "DATING";
    public static final String RELATIONSHIP_status_married = "MARRIED";
    public static final String RELATIONSHIP_status_complicated = "COMPLICATED";
    public static final String RELATIONSHIP_status_paused = "PAUSED";
    public static final String RELATIONSHIP_status_ended = "ENDED";


    /*********************
     * RELATIONSHIP ACHIEVEMENT LIST
     *********************/

    public static final String RELATIONSHIP_ACHIEVEMENT_LIST_SEGMENT_NAME = "relationship_achievement_list";
    public static final String RELATIONSHIP_ACHIEVEMENT_LIST_rel_ach_list_uuid = "rel_ach_list_uuid";


    /*********************
     * RELATIONSHIP ACHIEVEMENT REQUIREMENTS
     *********************/

    public static final String RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_SEGMENT_NAME = "relationship_achievement_requirements";
    public static final String RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_id = "rel_ach_req_id";
    public static final String RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_title = "rel_ach_req_title";
    public static final String RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_description = "rel_ach_req_description";


    /*********************
     * RELATIONSHIP ACHIEVEMENTS
     *********************/

    public static final String RELATIONSHIP_ACHIEVEMENTS_SEGMENT_NAME = "relationship_achievements";
    public static final String RELATIONSHIP_ACHIEVEMENTS_rel_ach_uuid = "rel_ach_uuid";
    public static final String RELATIONSHIP_ACHIEVEMENTS_rel_ach_list_uuid = "rel_ach_list_uuid";
    public static final String RELATIONSHIP_ACHIEVEMENTS_rel_ach_req_uuid = "rel_ach_req_uuid";


    /*********************
     * RELATIONSHIP REQUEST
     *********************/

    public static final String RELATIONSHIP_REQUEST_SEGMENT_NAME = "relationship_request";
    public static final String RELATIONSHIP_REQUEST_rel_request_uuid = "rel_request_uuid";
    public static final String RELATIONSHIP_REQUEST_rel_request_sender_user_uuid = "rel_request_sender_user_uuid";
    public static final String RELATIONSHIP_REQUEST_rel_request_recipient_user_name = "rel_request_recipient_user_name";
    public static final String RELATIONSHIP_REQUEST_rel_request_recipient_user_email = "rel_request_recipient_user_email";
    public static final String RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id = "rel_request_recipient_user_uuid";
    public static final String RELATIONSHIP_REQUEST_rel_request_status = "rel_request_status";
    public static final String RELATIONSHIP_REQUEST_rel_request_comment = "rel_request_comment";
    public static final String RELATIONSHIP_REQUEST_rel_desired_status = "rel_request_desired_rel_status";
    public static final String RELATIONSHIP_REQUEST_rel_is_secret = "rel_request_rel_is_secret";

    public static final String RELATIONSHIP_REQUEST_rel_request_status_requested = "REQUESTED";
    public static final String RELATIONSHIP_REQUEST_rel_request_status_accepted = "ACCEPTED";
    public static final String RELATIONSHIP_REQUEST_rel_request_status_rejected = "REJECTED";
    public static final String RELATIONSHIP_REQUEST_rel_request_status_inactive = "INACTIVE";
    public static final String RELATIONSHIP_REQUEST_rel_request_status_cancelled = "CANCELLED";



    /*********************
     * USER ADDRESS
     *********************/

    public static final String USER_ADDRESS_SEGMENT_NAME = "user_address";
    public static final String USER_ADDRESS_address_uuid = "address_uuid";
    public static final String USER_ADDRESS_adr_country = "adr_country";
    public static final String USER_ADDRESS_adr_city = "adr_city";
    public static final String USER_ADDRESS_adr_region = "adr_region";


    /*********************
     * USER LEVEL
     *********************/

    public static final String USER_LEVEL_SEGMENT_NAME = "user_level";
    public static final String USER_LEVEL_user_level_uuid = "user_level_uuid";
    public static final String USER_LEVEL_user_level_number = "user_level_number";


    /*********************
     * USERS
     *********************/

    public static final String USERS_SEGMENT_NAME = "users";
    public static final String USERS_user_uuid = "user_uuid";
    public static final String USERS_user_auth_provider = "user_auth_provider";
    public static final String USERS_user_auth_id = "user_auth_id";
    public static final String USERS_user_email = "user_email";
    public static final String USERS_user_firstname = "user_firstname";
    public static final String USERS_user_lastname = "user_lastname";
    public static final String USERS_user_nickname = "user_nickname";
    public static final String USERS_user_gender = "user_gender";
    public static final String USERS_user_age = "user_age";
    public static final String USERS_user_status = "user_status";
    public static final String USERS_user_joined = "user_joined";
    public static final String USERS_user_logged_in_last = "user_logged_in_last";
    public static final String USERS_user_total_points = "user_total_points";
    public static final String USERS_user_image_uuid = "user_image_uuid";
    public static final String USERS_address_uuid = "address_uuid";
    public static final String USERS_friends_list_uuid = "friends_list_uuid";
    public static final String USERS_user_level_uuid = "user_level_uuid";


    /*********************
     * USER REGISTRATION
     *********************/

    public static final String USER_REG_SEGMENT_NAME = "users";
    public static final String USER_REG_user_auth_provider = "user_auth_provider";
    public static final String USER_REG_user_auth_id = "user_auth_id";
    public static final String USER_REG_user_email = "user_email";
    public static final String USER_REG_user_firstname = "user_firstname";
    public static final String USER_REG_user_lastname = "user_lastname";
    public static final String USER_REG_user_nickname = "user_nickname";
    public static final String USER_REG_user_gender = "user_gender";
    public static final String USER_REG_user_age = "user_age";
    public static final String USER_REG_user_country= "adr_country";
    public static final String USER_REG_user_city = "adr_city";


    /*********************
     * TIMESTAMP PARAMETERS
     *********************/

    public static final String TIMESTAMP_CREATED = "created_utc";
    public static final String TIMESTAMP_LAST_MODIFIED = "last_modified_utc";


    /*********************
     * HEADER PARAMETERS
     *********************/

    public static final String CONTENT_LANG_HEADER = "Content-Language";
    public static final String ACCEPTED_ISO_COUNTRY_CODE = "Accept-Iso-Country-Code";

    /*********************
     * MEDIA PARAMETERS
     *********************/

    public static final String MEDIA_USER = "media_user";


    /*********************
     * VIEWS and FIELDS
     *********************/

    // RELATIONSHIP
    public static final String RELATIONSHIP_DATA_SEGMENT_NAME = "relationshipdata";
    public static final String COMBINED_RELATIONSHIP_USER_PARAMETER = "*, user_uuid_1{*}, user_uuid_2{*}";

    // POINT
    public static final String ADD_POINTS_SEGMENT_NAME = "add_points";
    public static final String COMBINED_POINT_EVENT_PARAMETER = "*,point{*},relationship{*,user1:user_uuid_1{*},user2:user_uuid_2{*}}";

    public static final String ADD_POINTS_Event_Data_FIELD = "points_event_data";
    public static final String ADD_POINTS_Data_FIELD = "points_data";

    // FRIENDSHIP REQUESTS
    public static final String EXTENDED_FRIEND_REQUEST_PARAMETER = "*, friend_request_sender_user_uuid{*}, friend_request_recipient_user_uuid{*}";

    // CREATE FRIENDSHIP
    public static final String CREATE_FRIENDSHIP_SEGMENT_NAME = "create_friendship";

    public static final String CREATE_FRIENDSHIP_user1 = "user1";
    public static final String CREATE_FRIENDSHIP_user2 = "user2";
}
