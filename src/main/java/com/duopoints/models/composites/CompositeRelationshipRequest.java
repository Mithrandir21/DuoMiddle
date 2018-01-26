package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.db.tables.pojos.Userdata;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class CompositeRelationshipRequest extends RelationshipRequest {

    private Userdata senderUser;
    private Userdata recipientUser;

    public CompositeRelationshipRequest(@NotNull RelationshipRequest value,@NotNull  Userdata senderUser,@Null Userdata recipientUser) {
        super(value);
        this.senderUser = senderUser;
        this.recipientUser = recipientUser;
    }

    @NotNull
    public Userdata getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(@NotNull Userdata senderUser) {
        this.senderUser = senderUser;
    }

    public Userdata getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(Userdata recipientUser) {
        this.recipientUser = recipientUser;
    }
}
