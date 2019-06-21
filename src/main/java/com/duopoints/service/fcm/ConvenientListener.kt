package com.duopoints.service.fcm

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

open class ConvenientListener : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) = Unit
    override fun onCancelled(databaseError: DatabaseError) = Unit
}
