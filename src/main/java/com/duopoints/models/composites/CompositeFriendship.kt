package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.Friendship
import com.duopoints.db.tables.pojos.Userdata

import javax.validation.constraints.NotNull

data class CompositeFriendship(val value: Friendship, val userOne: Userdata, val userTwo: Userdata) : Friendship(value)