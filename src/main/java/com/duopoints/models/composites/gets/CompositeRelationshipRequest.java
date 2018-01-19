package com.duopoints.models.composites.gets;

import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.db.tables.pojos.Userdata;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class CompositeRelationshipRequest extends RelationshipRequest {

    private Userdata senderUser;
    private Userdata recepientUser;

    public CompositeRelationshipRequest(@NotNull RelationshipRequest value,@NotNull  Userdata senderUser,@Null Userdata recepientUser) {
        super(value);
        this.senderUser = senderUser;
        this.recepientUser = recepientUser;
    }

    @NotNull
    public Userdata getSenderUser() {
        return senderUser;
    }

    public void setSenderUser(@NotNull Userdata senderUser) {
        this.senderUser = senderUser;
    }

    public Userdata getRecepientUser() {
        return recepientUser;
    }

    public void setRecepientUser(Userdata recepientUser) {
        this.recepientUser = recepientUser;
    }
}
