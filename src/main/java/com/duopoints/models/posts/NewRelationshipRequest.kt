package com.duopoints.models.posts

import com.duopoints.RequestParameters
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.UUID

data class NewRelationshipRequest(
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_sender_user_uuid) val senderUserID: UUID,
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_name) val recipientUserName: String,
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_email) val recipientUserEmail: String,
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_recipient_user_db_id) val recipientUserID: UUID,
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_request_comment) val requestComment: String,
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_desired_status) val requestRelDesiredStatus: String,
        @JsonProperty(RequestParameters.RELATIONSHIP_REQUEST_rel_is_secret) val isRequestRelIsSecret: Boolean = false
)