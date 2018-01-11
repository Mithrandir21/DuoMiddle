package com.duopoints.service;

import com.duopoints.db.tables.pojos.Friendrequest;
import com.duopoints.errorhandling.ConflictException;
import com.duopoints.models.RequestParameters;
import com.duopoints.models.posts.NewFriendRequest;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.duopoints.db.tables.Friendrequest.FRIENDREQUEST;

@Service
public class FriendService {

    @Autowired
    @Qualifier("configuration")
    private Configuration duoConfig;


    /*****************
     * FRIEND REQUEST
     *****************/

    public Friendrequest getFriendRequest(@NotNull UUID friendRequestID) {
        return duoConfig.dsl().selectFrom(FRIENDREQUEST).where(FRIENDREQUEST.FRIENDREQUESTDB_ID.eq(friendRequestID)).fetchOneInto(Friendrequest.class);
    }

    public Friendrequest createFriendRequest(@NotNull NewFriendRequest newFriendRequest) {
        // First check if there already exists a friend request for the given Sender and Recipient that is not CANCELLED
        Friendrequest friendrequest = duoConfig.dsl().selectFrom(FRIENDREQUEST)
                .where(FRIENDREQUEST.FRIENDREQUEST_SENDER_USERDB_ID.eq(newFriendRequest.requestSenderID),
                        FRIENDREQUEST.FRIENDREQUEST_RECIPIENT_USERDB_ID.eq(newFriendRequest.requestRecipientID))
                .and(FRIENDREQUEST.FRIENDREQUEST_STATUS.notEqual(RequestParameters.FRIEND_REQUEST_friend_request_status_cancelled))
                .fetchOneInto(Friendrequest.class);

        if (friendrequest != null) {
            throw new ConflictException("A friend request already exists with the status:" + friendrequest.getFriendrequestStatus());
        }

        return duoConfig.dsl().insertInto(FRIENDREQUEST)
                .columns(FRIENDREQUEST.FRIENDREQUEST_SENDER_USERDB_ID, FRIENDREQUEST.FRIENDREQUEST_RECIPIENT_USERDB_ID,
                        FRIENDREQUEST.FRIENDREQUEST_COMMENT, FRIENDREQUEST.FRIENDREQUEST_STATUS)
                .values(newFriendRequest.requestSenderID, newFriendRequest.requestRecipientID, newFriendRequest.requestComment,
                        RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)
                .returning()
                .fetchOne()
                .into(Friendrequest.class);
    }



}
