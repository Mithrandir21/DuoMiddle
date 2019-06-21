package com.duopoints.models

import com.duopoints.db.tables.pojos.Relationship
import com.duopoints.db.tables.pojos.Userdata
import com.duopoints.models.composites.CompositePointEvent
import com.duopoints.models.composites.CompositeRelationship

data class FullRelationshipData(
        private val relValue: Relationship,
        private val userOneData: Userdata,
        private val userTwoData: Userdata,
        val compositePointEvents: List<CompositePointEvent>
) : CompositeRelationship(relValue, userOneData, userTwoData)
