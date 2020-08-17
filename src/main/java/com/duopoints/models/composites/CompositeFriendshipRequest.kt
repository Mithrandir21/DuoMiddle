package com.duopoints.models.composites

import com.duopoints.db.tables.pojos.FriendRequest
import com.duopoints.db.tables.pojos.Userdata

data class CompositeFriendshipRequest(val value: FriendRequest, val senderUser: Userdata, val recipientUser: Userdata) : FriendRequest(value)