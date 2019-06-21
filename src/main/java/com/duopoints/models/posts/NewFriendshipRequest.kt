package com.duopoints.models.posts

import com.duopoints.RequestParameters
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.UUID

data class NewFriendshipRequest(
        @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_sender_user_uuid) val requestSenderID: UUID,
        @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_recipient_user_uuid) val requestRecipientID: UUID,
        @JsonProperty(RequestParameters.FRIENDSHIP_REQUEST_friend_request_comment) val requestComment: String
)