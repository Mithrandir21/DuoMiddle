package com.duopoints.service;

import com.duopoints.db.tables.pojos.Friendrequest;
import com.duopoints.db.tables.pojos.Friendrights;
import com.duopoints.db.tables.pojos.Friendship;
import com.duopoints.db.tables.records.FriendrequestRecord;
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

import static com.duopoints.db.tables.Friendrequest.FRIENDREQUEST;
import static com.duopoints.db.tables.Friendrights.FRIENDRIGHTS;
import static com.duopoints.db.tables.Friendship.FRIENDSHIP;

@Service
public class FriendService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;


    /*****************
     * FRIEND REQUEST
     *****************/

    public Friendrequest getFriendRequest(@NotNull UUID friendRequestID) {
        return duo.selectFrom(FRIENDREQUEST).where(FRIENDREQUEST.FRIENDREQUESTDB_ID.eq(friendRequestID)).fetchOneInto(Friendrequest.class);
    }

    public Friendrequest createFriendRequest(@NotNull NewFriendRequest newFriendRequest) {
        // First check if there already exists a friend request for the given Sender and Recipient that is not WAITING_FOR_RECIPIENT
        Friendrequest friendrequest = duo.selectFrom(FRIENDREQUEST)
                .where(FRIENDREQUEST.FRIENDREQUEST_SENDER_USERDB_ID.eq(newFriendRequest.requestSenderID),
                        FRIENDREQUEST.FRIENDREQUEST_RECIPIENT_USERDB_ID.eq(newFriendRequest.requestRecipientID))
                .and(FRIENDREQUEST.FRIENDREQUEST_STATUS.in(RequestParameters.FRIEND_REQUEST_friend_request_status_sent,
                        RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .fetchOneInto(Friendrequest.class);

        if (friendrequest != null) {
            throw new ConflictException("A friend request already exists with the status:" + friendrequest.getFriendrequestStatus());
        }

        return duo.insertInto(FRIENDREQUEST)
                .columns(FRIENDREQUEST.FRIENDREQUEST_SENDER_USERDB_ID, FRIENDREQUEST.FRIENDREQUEST_RECIPIENT_USERDB_ID,
                        FRIENDREQUEST.FRIENDREQUEST_COMMENT, FRIENDREQUEST.FRIENDREQUEST_STATUS)
                .values(newFriendRequest.requestSenderID, newFriendRequest.requestRecipientID, newFriendRequest.requestComment,
                        RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)
                .returning()
                .fetchOne()
                .into(Friendrequest.class);
    }

    @Transactional
    public Friendrequest setFinalFriendRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        // First we retrieve the Request
        Friendrequest friendrequest = duo.selectFrom(FRIENDREQUEST)
                .where(FRIENDREQUEST.FRIENDREQUESTDB_ID.eq(requestID)).fetchOneInto(Friendrequest.class);

        if (friendrequest == null) {
            throw new NoMatchingRowException("No Friendrequest found matching requestID='" + requestID + "'");
        }

        // If the Request is anything other than REQUESTED, fail.
        if (!friendrequest.getFriendrequestStatus().equalsIgnoreCase(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient)) {
            throw new ConflictException("RelationshipRequest has status:'" + friendrequest.getFriendrequestStatus() + "'");
        }

        if (status.equalsIgnoreCase(RequestParameters.FRIEND_REQUEST_friend_request_status_completed)) {
            if (createFriend(friendrequest.getFriendrequestSenderUserdbId(), friendrequest.getFriendrequestRecipientUserdbId()) == null) {
                throw new NoMatchingRowException("No Friendship created! Error!");
            }
        }

        // Now set the Status of the Request
        FriendrequestRecord friendrequestRecord = duo.update(FRIENDREQUEST)
                .set(FRIENDREQUEST.FRIENDREQUEST_STATUS, status)
                .where(FRIENDREQUEST.FRIENDREQUESTDB_ID.eq(requestID))
                .and(FRIENDREQUEST.FRIENDREQUEST_STATUS.eq(RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient))
                .returning()
                .fetchOne();

        if (friendrequestRecord != null) {
            return friendrequestRecord.into(Friendrequest.class);
        } else {
            throw new NoMatchingRowException("No Friendrequest found matching requestID='" + requestID + "' having status " + RequestParameters.FRIEND_REQUEST_friend_request_status_waiting_for_recipient);
        }
    }


    /*****************
     * FRIENDSHIP
     *****************/

    public Friendship getActiveFriendship(@NotNull UUID userOne, @NotNull UUID userTwo) {
        Condition friends1 = FRIENDSHIP.USERONEDB_ID.eq(userOne).and(FRIENDSHIP.USERTWODB_ID.eq(userTwo));
        Condition friends2 = FRIENDSHIP.USERONEDB_ID.eq(userTwo).and(FRIENDSHIP.USERTWODB_ID.eq(userOne));

        return duo.selectFrom(FRIENDSHIP).where(friends1).or(friends2)
                .and(FRIENDSHIP.FRIENDSHIPSTATUS.eq(RequestParameters.FRIEND_friendship_status_active))
                .fetchOneInto(Friendship.class);
    }

    private Friendship createFriend(@NotNull UUID userOne, @NotNull UUID userTwo) {
        // First check if users already friends
        if (getActiveFriendship(userOne, userTwo) != null) {
            throw new ConflictException("Friendship already exists between userOne('" + userOne + "') and userTwo('" + userTwo + "')");
        }

        // At this point, we need to create FriendRights for each friend in this relationship
        Friendrights rightsOne = duo.insertInto(FRIENDRIGHTS).defaultValues().returning().fetchOne().into(Friendrights.class);
        Friendrights rightsTwo = duo.insertInto(FRIENDRIGHTS).defaultValues().returning().fetchOne().into(Friendrights.class);


        return duo.insertInto(FRIENDSHIP)
                .columns(FRIENDSHIP.USERONEDB_ID, FRIENDSHIP.USERTWODB_ID, FRIENDSHIP.FRIENDONERIGHTSDB_ID, FRIENDSHIP.FRIENDTWORIGHTSDB_ID, FRIENDSHIP.FRIENDSHIPSTATUS)
                .values(userOne, userTwo, rightsOne.getFriendrightsdbId(), rightsTwo.getFriendrightsdbId(), RequestParameters.FRIEND_friendship_status_active)
                .returning()
                .fetchOne()
                .into(Friendship.class);
    }
}