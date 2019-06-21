package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.RelationshipRequest
import com.duopoints.db.tables.pojos.Userdata

import javax.validation.constraints.NotNull
import javax.validation.constraints.Null

data class CompositeRelationshipRequest(val value: RelationshipRequest, val senderUser: Userdata, val recipientUser: Userdata) : RelationshipRequest(value)