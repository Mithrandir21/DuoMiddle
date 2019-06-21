package com.duopoints.models

import com.duopoints.models.composites.CompositePointEvent
import com.duopoints.models.composites.CompositeUserLevel

data class UserFeed(val compositeUserLevels: List<CompositeUserLevel>, val compositePointEvents: List<CompositePointEvent>)