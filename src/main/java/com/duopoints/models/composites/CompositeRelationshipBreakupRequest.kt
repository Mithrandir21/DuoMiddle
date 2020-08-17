package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.RelationshipBreakupRequest
import com.duopoints.db.tables.pojos.Userdata

data class CompositeRelationshipBreakupRequest(val value: RelationshipBreakupRequest, val relationship: CompositeRelationship, val user: Userdata) : RelationshipBreakupRequest(value)