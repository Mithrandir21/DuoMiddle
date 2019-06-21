package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.Relationship
import com.duopoints.db.tables.pojos.Userdata

import javax.validation.constraints.NotNull

open class CompositeRelationship(val value: Relationship, val userOne: Userdata, val userTwo: Userdata) : Relationship(value)