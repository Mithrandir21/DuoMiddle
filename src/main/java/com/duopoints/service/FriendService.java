package com.duopoints.service;

import com.duopoints.db.tables.pojos.FriendRequest;
import com.duopoints.db.tables.pojos.FriendRights;
import com.duopoints.db.tables.pojos.Friendship;
import com.duopoints.db.tables.records.FriendRequestRecord;
import com.duopoints.errorhandling.ConflictException;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.RequestParameters;
import com.duopoints.models.posts.NewFriendRequest;
import org.jooq.Condition;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.duopoints.db.tables.FriendRequest.FRIEND_REQUEST;
import static com.duopoints.db.tables.FriendRights.FRIEND_RIGHTS;
import static com.duopoints.db.tables.Friendship.FRIENDSHIP;

@Service
public class FriendService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;


    /*****************
     * FRIEND REQUEST
     *****************/

    public FriendRequest getFriendRequest(@NotNull UUID friendRequestID) {
        return duo.selectFrom(FRIEND_REQUEST).where(FRIEND_REQUEST.FRIEND_REQUEST_UUID.eq(friendRequestID)).fetchOneInto(FriendRequest.class);
    }

    public FriendRequest createFriendRequest(@NotNull NewFriendRequest newFriendRequest) {
        // First check if there already exists a friend request for the given Sender and Recipient that is not WAITING_FOR_RECIPIENT
        FriendRequest friendrequest = duo.selectFrom(FRIEND_REQUEST)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_SENDER_USER_UUID.eq(newFriendRequest.requestSenderID),
                        FRIEND_REQUEST.FRIEND_REQUEST_RECIPIENT_USER_UUID.eq(newFriendRequest.requestRecipientID))
                .and(FRIEND_REQUEST.FRIEND_REQUEST_STATUS.in(RequestParameters.FRIEND_REQUEST_friend_request_status_sent,
                        RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .fetchOneInto(FriendRequest.class);

        if (friendrequest != null) {
            throw new ConflictException("A friend request already exists with the status:" + friendrequest.getFriendRequestStatus());
        }

        return duo.insertInto(FRIEND_REQUEST)
                .columns(FRIEND_REQUEST.FRIEND_REQUEST_SENDER_USER_UUID, FRIEND_REQUEST.FRIEND_REQUEST_RECIPIENT_USER_UUID,
                        FRIEND_REQUEST.FRIEND_REQUEST_COMMENT, FRIEND_REQUEST.FRIEND_REQUEST_STATUS)
                .values(newFriendRequest.requestSenderID, newFriendRequest.requestRecipientID, newFriendRequest.requestComment,
                        RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)
                .returning()
                .fetchOne()
                .into(FriendRequest.class);
    }

    @Transactional
    public FriendRequest setFinalFriendRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        // First we retrieve the Request
        FriendRequest friendrequest = duo.selectFrom(FRIEND_REQUEST)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_UUID.eq(requestID)).fetchOneInto(FriendRequest.class);

        if (friendrequest == null) {
            throw new NoMatchingRowException("No FriendRequest found matching requestID='" + requestID + "'");
        }

        // If the Request is anything other than REQUESTED, fail.
        if (!friendrequest.getFriendRequestStatus().equalsIgnoreCase(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)) {
            throw new ConflictException("RelationshipRequest has status:'" + friendrequest.getFriendRequestStatus() + "'");
        }

        if (status.equalsIgnoreCase(RequestParameters.FRIEND_REQUEST_friend_request_status_completed)) {
            if (createFriend(friendrequest.getFriendRequestSenderUserUuid(), friendrequest.getFriendRequestRecipientUserUuid()) == null) {
                throw new NoMatchingRowException("No Friendship created! Error!");
            }
        }

        // Now set the Status of the Request
        FriendRequestRecord friendrequestRecord = duo.update(FRIEND_REQUEST)
                .set(FRIEND_REQUEST.FRIEND_REQUEST_STATUS, status)
                .where(FRIEND_REQUEST.FRIEND_REQUEST_UUID.eq(requestID))
                .and(FRIEND_REQUEST.FRIEND_REQUEST_STATUS.eq(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .returning()
                .fetchOne();

        if (friendrequestRecord != null) {
            return friendrequestRecord.into(FriendRequest.class);
        } else {
            throw new NoMatchingRowException("No FriendRequest found matching requestID='" + requestID + "' having status " + RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient);
        }
    }


    /*****************
     * FRIENDSHIP
     *****************/

    public Friendship getActiveFriendship(@NotNull UUID userOne, @NotNull UUID userTwo) {
        Condition friends1 = FRIENDSHIP.USER_ONE_UUID.eq(userOne).and(FRIENDSHIP.USER_TWO_UUID.eq(userTwo));
        Condition friends2 = FRIENDSHIP.USER_ONE_UUID.eq(userTwo).and(FRIENDSHIP.USER_TWO_UUID.eq(userOne));

        return duo.selectFrom(FRIENDSHIP).where(friends1).or(friends2)
                .and(FRIENDSHIP.FRIENDSHIP_STATUS.eq(RequestParameters.FRIEND_friendship_status_active))
                .fetchOneInto(Friendship.class);
    }

    private Friendship createFriend(@NotNull UUID userOne, @NotNull UUID userTwo) {
        // First check if users already friends
        if (getActiveFriendship(userOne, userTwo) != null) {
            throw new ConflictException("Friendship already exists between userOne('" + userOne + "') and userTwo('" + userTwo + "')");
        }

        // At this point, we need to create FriendRights for each friend in this relationship
        FriendRights rightsOne = duo.insertInto(FRIEND_RIGHTS).defaultValues().returning().fetchOne().into(FriendRights.class);
        FriendRights rightsTwo = duo.insertInto(FRIEND_RIGHTS).defaultValues().returning().fetchOne().into(FriendRights.class);


        return duo.insertInto(FRIENDSHIP)
                .columns(FRIENDSHIP.USER_ONE_UUID, FRIENDSHIP.USER_TWO_UUID, FRIENDSHIP.FRIEND_ONE_RIGHTS_UUID, FRIENDSHIP.FRIEND_TWO_RIGHTS_UUID, FRIENDSHIP.FRIENDSHIP_STATUS)
                .values(userOne, userTwo, rightsOne.getFriendRightsUuid(), rightsTwo.getFriendRightsUuid(), RequestParameters.FRIEND_friendship_status_active)
                .returning()
                .fetchOne()
                .into(Friendship.class);
    }
}