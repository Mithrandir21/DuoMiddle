package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.RelationshipRequest
import com.duopoints.db.tables.pojos.Userdata

data class CompositeRelationshipRequest(val value: RelationshipRequest, val senderUser: Userdata, val recipientUser: Userdata) : RelationshipRequest(value)