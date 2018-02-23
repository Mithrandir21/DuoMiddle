package com.duopoints.service.fcm;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ConvenientListener implements ValueEventListener {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // Empty
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        // Empty
    }
}
