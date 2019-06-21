package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.UserLevel
import com.duopoints.db.tables.pojos.Userdata

data class CompositeUserLevel(val value: UserLevel, val user: Userdata) : UserLevel(value)