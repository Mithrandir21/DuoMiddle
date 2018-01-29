package com.duopoints.models.composites;

import com.duopoints.db.tables.pojos.FriendRequest;
import com.duopoints.db.tables.pojos.Userdata;

import javax.validation.constraints.NotNull;

public class CompositeFriendshipRequest extends FriendRequest {

    private Userdata senderUser;
    private Userdata recipientUser;

    public CompositeFriendshipRequest(@NotNull FriendRequest value, @NotNull Userdata senderUser, @NotNull Userdata recipientUser) {
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

    @NotNull
    public Userdata getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(@NotNull Userdata recipientUser) {
        this.recipientUser = recipientUser;
    }
}
