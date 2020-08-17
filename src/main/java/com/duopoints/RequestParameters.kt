package com.duopoints

object RequestParameters {
    const val CALLER_UNNECESSARY = -1
    const val DELIMITER = "/"
    const val PROCEDURE_SEGMENT = "rpc"
    const val EXPLORE = "explore"
    const val SEARCH = "search"
    const val SEARCH_QUERY = "q"
    const val SESSIONS = "sessions"
    const val AUTHENTICATE = "authenticate"
    const val REGISTER = "register"
    const val FIRST_USE = "first_use"
    const val USERS = "users"
    const val PING = "ping"
    const val LOVE = "love"
    const val UNLOVE = "unlove"
    const val HATE = "hate"
    const val INFO = "info"
    const val SELECT = "select"

    /*********************
     * HEADER PARAMETERS
     */
    const val PREFER = "Prefer"
    const val REPRESENTATION = "return=representation"

    /*********************
     * USERDATA PARAMETERS
     */
    const val USERDATA_SEGMENT = "userdata"

    /*********************
     * BLOCKED USERS
     */
    const val BLOCKED_USERS_SEGMENT_NAME = "blocked_users"
    const val BLOCKED_USERS_blocking_user_uuid = "blocking_user_uuid"
    const val BLOCKED_USERS_blocked_user_uuid = "blocked_user_uuid"

    /*********************
     * FRIEND
     */
    const val FRIEND_SEGMENT_NAME = "friend"
    const val FRIEND_friend_uuid = "friend_uuid"
    const val FRIEND_user_uuid = "user_uuid"
    const val FRIEND_friends_list_uuid = "friends_list_uuid"
    const val FRIEND_friend_rights_uuid = "friend_rights_uuid"
    const val FRIEND_friendship_status = "friendship_status"
    const val FRIEND_friendship_status_active = "ACTIVE"
    const val FRIEND_friendship_status_ended = "ENDED"

    /*********************
     * FRIENDSHIP REQUEST
     */
    const val FRIENDSHIP_REQUEST_SEGMENT_NAME = "friendship_request"
    const val FRIENDSHIP_REQUEST_friend_request_uuid = "friendship_request_uuid"
    const val FRIENDSHIP_REQUEST_friend_request_sender_user_uuid = "friendship_request_sender_user_uuid"
    const val FRIENDSHIP_REQUEST_friend_request_recipient_user_uuid = "friendship_request_recipient_user_uuid"
    const val FRIENDSHIP_REQUEST_friend_request_comment = "friendship_request_comment"
    const val FRIENDSHIP_REQUEST_friend_request_status = "friendship_request_status"
    const val FRIEND_REQUEST_friend_request_status_waiting_for_recipient = "WAITING_FOR_RECIPIENT"
    const val FRIEND_REQUEST_friend_request_status_completed = "COMPLETED"
    const val FRIEND_REQUEST_friend_request_status_rejected = "REJECTED"
    const val FRIEND_REQUEST_friend_request_status_cancelled = "CANCELLED"
    const val FRIEND_REQUEST_friend_request_status_inactive = "INACTIVE"

    /*********************
     * FRIEND RIGHTS
     */
    const val FRIEND_RIGHTS_SEGMENT_NAME = "friend_rights"
    const val FRIEND_RIGHTS_friend_rights_uuid = "friend_rights_uuid"
    const val FRIEND_RIGHTS_friend_can_see_relationship = "friend_can_see_relationship"
    const val FRIEND_RIGHTS_friend_can_see_timeline = "friend_can_see_timeline"
    const val FRIEND_RIGHTS_friend_can_see_history = "friend_can_see_history"

    /*********************
     * LEVEL POINT REQUIREMENTS
     */
    const val LEVEL_POINT_REQUIREMENTS_SEGMENT_NAME = "LEVEL_POINT_REQUIREMENTS"
    const val LEVEL_POINT_REQUIREMENTS_level_number = "level_number"
    const val LEVEL_POINT_REQUIREMENTS_level_point_requirement = "level_point_requirement"

    /*********************
     * MEDIA OBJECT
     */
    const val MEDIA_OBJECT_SEGMENT_NAME = "media_object"
    const val MEDIA_OBJECT_media_object_uuid = "media_object_uuid"
    const val MEDIA_OBJECT_media_object_desc = "media_object_desc"
    const val MEDIA_OBJECT_media_object_type = "media_object_type"

    /*********************
     * POINT
     */
    const val POINT_SEGMENT_NAME = "point"
    const val POINT_uuid = "point_uuid"
    const val POINT_event_uuid = "point_event_uuid"
    const val POINT_value = "point_value"
    const val POINT_type_number = "point_type_number"
    const val POINT_chain_position = "point_chain_position"
    const val POINT_comment = "point_comment"

    /*********************
     * POINT EVENT
     */
    const val POINT_EVENT_SEGMENT_NAME = "point_event"
    const val POINT_EVENT_point_event_uuid = "point_event_uuid"
    const val POINT_EVENT_point_giver_user_uuid = "point_giver_user_uuid"
    const val POINT_EVENT_relationship_uuid = "relationship_uuid"
    const val POINT_EVENT_point_event_emotion_number = "point_event_emotion_number"
    const val POINT_EVENT_point_event_timestamp = "point_event_timestamp"
    const val POINT_EVENT_point_event_timezone = "point_event_timezone"
    const val POINT_EVENT_point_event_type = "point_event_type"
    const val POINT_EVENT_point_event_status = "point_event_status"
    const val POINT_EVENT_point_event_status_comment = "point_event_status_comment"
    const val POINT_EVENT_point_event_title = "point_event_title"
    const val POINT_EVENT_point_event_comment = "point_event_comment"

    /*********************
     * POINT EVENT EMOTION
     */
    const val POINT_EVENT_EMOTION_SEGMENT_NAME = "point_event_emotion"
    const val POINT_EVENT_EMOTION_point_event_emotion_number = "point_event_emotion_number"
    const val POINT_EVENT_EMOTION_point_event_emotion_title = "point_event_emotion_title"
    const val POINT_EVENT_EMOTION_point_event_emotion_description = "point_event_emotion_description"
    const val POINT_EVENT_EMOTION_status_active = "ACTIVE"
    const val POINT_EVENT_EMOTION_status_deactivated = "DEACTIVATED"

    /*********************
     * POINT TYPE
     */
    const val POINT_TYPE_SEGMENT_NAME = "point_type"
    const val POINT_TYPE_point_type_number = "point_type_number"
    const val POINT_TYPE_point_type_category_number = "point_type_category_number"
    const val POINT_TYPE_point_type_title = "point_type_title"
    const val POINT_TYPE_point_type_description = "point_type_description"
    const val POINT_TYPE_status_active = "ACTIVE"
    const val POINT_TYPE_status_deactivated = "DEACTIVATED"

    /*********************
     * POINT TYPE CATEGORY
     */
    const val POINT_TYPE_CATEGORY_SEGMENT_NAME = "point_type_category"
    const val POINT_TYPE_CATEGORY_point_type_category_number = "point_type_category_number"
    const val POINT_TYPE_CATEGORY_point_type_category_title = "point_type_category_title"
    const val POINT_TYPE_CATEGORY_point_type_category_description = "point_type_category_description"
    const val POINT_TYPE_CATEGORY_status_active = "ACTIVE"
    const val POINT_TYPE_CATEGORY_status_deactivated = "DEACTIVATED"

    /*********************
     * REL BREAKUP REQUEST
     */
    const val REL_BREAKUP_REQUEST_SEGMENT_NAME = "relationship_breakup_request"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_uuid = "relationship_breakup_requestdb_id"
    const val REL_BREAKUP_REQUEST_rel_breakup_requesting_user_uuid = "relationship_breakup_requesting_user_uuid"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_relationship_uuid = "relationship_breakup_request_relationship_uuid"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_comment = "relationship_breakup_request_comment"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_status = "relationship_breakup_request_status"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_status_completed = "COMPLETED"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_status_processing = "PROCESSING"
    const val REL_BREAKUP_REQUEST_rel_breakup_request_status_cancelled = "CANCELLED"

    /*********************
     * RELATIONSHIP
     */
    const val RELATIONSHIP_SEGMENT_NAME = "relationship"
    const val RELATIONSHIP_relationship_uuid = "relationship_uuid"
    const val RELATIONSHIP_user_uuid_1 = "user_uuid_1"
    const val RELATIONSHIP_user_uuid_2 = "user_uuid_2"
    const val RELATIONSHIP_rel_ach_list_uuid = "rel_ach_list_uuid"
    const val RELATIONSHIP_start_date = "start_date"
    const val RELATIONSHIP_end_date = "end_date"
    const val RELATIONSHIP_auto_accept_points = "auto_accept_points"
    const val RELATIONSHIP_relationship_image_uuid = "relationship_image_uuid"
    const val RELATIONSHIP_status = "status"
    const val RELATIONSHIP_is_secret = "is_secret"
    const val RELATIONSHIP_revivable = "revivable"
    const val RELATIONSHIP_rel_title = "rel_title"
    const val RELATIONSHIP_rel_desc = "rel_desc"
    const val RELATIONSHIP_rel_total_points = "rel_total_points"
    const val RELATIONSHIP_status_dating = "DATING"
    const val RELATIONSHIP_status_married = "MARRIED"
    const val RELATIONSHIP_status_complicated = "COMPLICATED"
    const val RELATIONSHIP_status_paused = "PAUSED"
    const val RELATIONSHIP_status_ended = "ENDED"

    /*********************
     * RELATIONSHIP ACHIEVEMENT LIST
     */
    const val RELATIONSHIP_ACHIEVEMENT_LIST_SEGMENT_NAME = "relationship_achievement_list"
    const val RELATIONSHIP_ACHIEVEMENT_LIST_rel_ach_list_uuid = "rel_ach_list_uuid"

    /*********************
     * RELATIONSHIP ACHIEVEMENT REQUIREMENTS
     */
    const val RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_SEGMENT_NAME = "relationship_achievement_requirements"
    const val RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_id = "rel_ach_req_id"
    const val RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_title = "rel_ach_req_title"
    const val RELATIONSHIP_ACHIEVEMENT_REQUIREMENTS_rel_ach_req_description = "rel_ach_req_description"

    /*********************
     * RELATIONSHIP ACHIEVEMENTS
     */
    const val RELATIONSHIP_ACHIEVEMENTS_SEGMENT_NAME = "relationship_achievements"
    const val RELATIONSHIP_ACHIEVEMENTS_rel_ach_uuid = "rel_ach_uuid"
    const val RELATIONSHIP_ACHIEVEMENTS_rel_ach_list_uuid = "rel_ach_list_uuid"
    const val RELATIONSHIP_ACHIEVEMENTS_rel_ach_req_uuid = "rel_ach_req_uuid"

    /*********************
     * RELATIONSHIP REQUEST
     */
    const val RELATIONSHIP_REQUEST_SEGMENT_NAME = "relationship_request"
    const val RELATIONSHIP_REQUEST_rel_request_uuid = "rel_request_uuid"
    const val RELATIONSHIP_REQUEST_rel_request_sender_user_uuid = "rel_request_sender_user_uuid"
    const val RELATIONSHIP_REQUEST_rel_request_recipient_user_name = "rel_request_recipient_user_name"
    const val RELATIONSHIP_REQUEST_rel_request_recipient_user_email = "rel_request_recipient_user_email"
    const val RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id = "rel_request_recipient_user_uuid"
    const val RELATIONSHIP_REQUEST_rel_request_status = "rel_request_status"
    const val RELATIONSHIP_REQUEST_rel_request_comment = "rel_request_comment"
    const val RELATIONSHIP_REQUEST_rel_desired_status = "rel_request_desired_rel_status"
    const val RELATIONSHIP_REQUEST_rel_is_secret = "rel_request_rel_is_secret"
    const val RELATIONSHIP_REQUEST_rel_request_status_requested = "REQUESTED"
    const val RELATIONSHIP_REQUEST_rel_request_status_accepted = "ACCEPTED"
    const val RELATIONSHIP_REQUEST_rel_request_status_rejected = "REJECTED"
    const val RELATIONSHIP_REQUEST_rel_request_status_inactive = "INACTIVE"
    const val RELATIONSHIP_REQUEST_rel_request_status_cancelled = "CANCELLED"

    /*********************
     * USER ADDRESS
     */
    const val USER_ADDRESS_SEGMENT_NAME = "user_address"
    const val USER_ADDRESS_address_uuid = "address_uuid"
    const val USER_ADDRESS_adr_country = "adr_country"
    const val USER_ADDRESS_adr_city = "adr_city"
    const val USER_ADDRESS_adr_region = "adr_region"

    /*********************
     * USER LEVEL
     */
    const val USER_LEVEL_SEGMENT_NAME = "user_level"
    const val USER_LEVEL_user_level_uuid = "user_level_uuid"
    const val USER_LEVEL_user_level_number = "user_level_number"

    /*********************
     * USERS
     */
    const val USERS_SEGMENT_NAME = "users"
    const val USERS_user_uuid = "user_uuid"
    const val USERS_user_auth_provider = "user_auth_provider"
    const val USERS_user_auth_id = "user_auth_id"
    const val USERS_user_email = "user_email"
    const val USERS_user_firstname = "user_firstname"
    const val USERS_user_lastname = "user_lastname"
    const val USERS_user_nickname = "user_nickname"
    const val USERS_user_gender = "user_gender"
    const val USERS_user_age = "user_age"
    const val USERS_user_status = "user_status"
    const val USERS_user_joined = "user_joined"
    const val USERS_user_logged_in_last = "user_logged_in_last"
    const val USERS_user_total_points = "user_total_points"
    const val USERS_user_image_uuid = "user_image_uuid"
    const val USERS_address_uuid = "address_uuid"
    const val USERS_friends_list_uuid = "friends_list_uuid"
    const val USERS_user_level_uuid = "user_level_uuid"

    /*********************
     * USER REGISTRATION
     */
    const val USER_REG_SEGMENT_NAME = "users"
    const val USER_REG_user_auth_provider = "user_auth_provider"
    const val USER_REG_user_auth_id = "user_auth_id"
    const val USER_REG_user_email = "user_email"
    const val USER_REG_user_firstname = "user_firstname"
    const val USER_REG_user_lastname = "user_lastname"
    const val USER_REG_user_nickname = "user_nickname"
    const val USER_REG_user_gender = "user_gender"
    const val USER_REG_user_age = "user_age"
    const val USER_REG_user_country = "adr_country"
    const val USER_REG_user_city = "adr_city"

    /*********************
     * TIMESTAMP PARAMETERS
     */
    const val TIMESTAMP_CREATED = "created_utc"
    const val TIMESTAMP_LAST_MODIFIED = "last_modified_utc"

    /*********************
     * HEADER PARAMETERS
     */
    const val CONTENT_LANG_HEADER = "Content-Language"
    const val ACCEPTED_ISO_COUNTRY_CODE = "Accept-Iso-Country-Code"

    /*********************
     * MEDIA PARAMETERS
     */
    const val MEDIA_USER = "media_user"

    /*********************
     * VIEWS and FIELDS
     */
    // RELATIONSHIP
    const val RELATIONSHIP_DATA_SEGMENT_NAME = "relationshipdata"
    const val COMBINED_RELATIONSHIP_USER_PARAMETER = "*, user_uuid_1{*}, user_uuid_2{*}"

    // POINT
    const val ADD_POINTS_SEGMENT_NAME = "add_points"
    const val COMBINED_POINT_EVENT_PARAMETER = "*,point{*},relationship{*,user1:user_uuid_1{*},user2:user_uuid_2{*}}"
    const val ADD_POINTS_Event_Data_FIELD = "points_event_data"
    const val ADD_POINTS_Data_FIELD = "points_data"

    // FRIENDSHIP REQUESTS
    const val EXTENDED_FRIEND_REQUEST_PARAMETER = "*, friend_request_sender_user_uuid{*}, friend_request_recipient_user_uuid{*}"

    // CREATE FRIENDSHIP
    const val CREATE_FRIENDSHIP_SEGMENT_NAME = "create_friendship"
    const val CREATE_FRIENDSHIP_user1 = "user1"
    const val CREATE_FRIENDSHIP_user2 = "user2"
}