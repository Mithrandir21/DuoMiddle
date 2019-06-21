package com.duopoints.models.posts

import java.util.UUID

data class NewRelationshipBreakupRequest(val requestingUserUUID: UUID, val relationshipUUID: UUID, val requestComment: String)