package com.duopoints.service;

import com.duopoints.RequestParameters;
import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.RelationshipBreakupRequest;
import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.db.tables.records.RelationshipBreakupRequestRecord;
import com.duopoints.db.tables.records.RelationshipRecord;
import com.duopoints.db.tables.records.RelationshipRequestRecord;
import com.duopoints.errorhandling.ConflictException;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.FullRelationshipData;
import com.duopoints.models.composites.CompositeRelationship;
import com.duopoints.models.composites.CompositeRelationshipBreakupRequest;
import com.duopoints.models.composites.CompositeRelationshipRequest;
import com.duopoints.models.posts.NewRelationshipBreakupRequest;
import com.duopoints.models.posts.NewRelationshipRequest;
import org.jooq.Condition;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.Relationship.RELATIONSHIP;
import static com.duopoints.db.tables.RelationshipBreakupRequest.RELATIONSHIP_BREAKUP_REQUEST;
import static com.duopoints.db.tables.RelationshipRequest.RELATIONSHIP_REQUEST;

@SuppressWarnings("WeakerAccess")
@Service
public class RelationshipService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;

    @Autowired
    private UserService userService;

    @Autowired
    private PointService pointService;

    /*********************
     * RELATIONSHIP
     *********************/

    @Nullable
    public Relationship getRelationship(@NotNull UUID relID) {
        return duo.selectFrom(RELATIONSHIP).where(RELATIONSHIP.RELATIONSHIP_UUID.eq(relID)).fetchOneInto(Relationship.class);
    }

    @Nullable
    public Relationship getActiveUserRelationship(@NotNull UUID userID) {
        return duo.selectFrom(RELATIONSHIP)
                .where(RELATIONSHIP.USER_UUID_1.eq(userID))
                .or(RELATIONSHIP.USER_UUID_2.eq(userID))
                .and(RELATIONSHIP.STATUS.notEqual(RequestParameters.RELATIONSHIP_status_ended))
                .fetchOneInto(Relationship.class);
    }

    @NotNull
    public List<Relationship> getActiveUsersRelationship(@NotNull List<UUID> userIDs) {
        return duo.selectFrom(RELATIONSHIP)
                .where(RELATIONSHIP.USER_UUID_1.in(userIDs))
                .or(RELATIONSHIP.USER_UUID_2.in(userIDs))
                .and(RELATIONSHIP.STATUS.notEqual(RequestParameters.RELATIONSHIP_status_ended))
                .fetchInto(Relationship.class);
    }

    @NotNull
    public CompositeRelationship getCompositeRelationship(@NotNull UUID relID) {
        Relationship relationship = getRelationship(relID);

        if (relationship != null) {
            return getCompositeRelationship(relationship);
        } else {
            throw new NoMatchingRowException("No Relationship found matching relID='" + relID + "'");
        }
    }

    public CompositeRelationship getCompositeRelationship(@NotNull Relationship relationship) {
        // Complete the composite object with Users needed
        return new CompositeRelationship(relationship, userService.getUser(relationship.getUserUuid_1()), userService.getUser(relationship.getUserUuid_2()));
    }

    @NotNull
    public CompositeRelationship getActiveUserCompositeRelationship(@NotNull UUID userID) {
        Relationship activeUserRelationship = getActiveUserRelationship(userID);

        if (activeUserRelationship != null) {
            return getCompositeRelationship(activeUserRelationship);
        } else {
            throw new NoMatchingRowException("No Active Relationship found for userID='" + userID + "'");
        }
    }

    private CompositeRelationship createRelationship(@NotNull UUID userOne, @NotNull UUID userTwo, @NotNull String relStatus, boolean isSecret) {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(userOne) != null) {
            throw new ConflictException("User(" + userOne + ") already in Active Relationship");
        }

        if (getActiveUserRelationship(userTwo) != null) {
            throw new ConflictException("User(" + userTwo + ") already in Active Relationship");
        }

        // Create Relationship
        Relationship relationship = duo.insertInto(RELATIONSHIP)
                .columns(RELATIONSHIP.USER_UUID_1, RELATIONSHIP.USER_UUID_2, RELATIONSHIP.STATUS, RELATIONSHIP.IS_SECRET)
                .values(userOne, userTwo, relStatus, isSecret)
                .returning()
                .fetchOne()
                .into(Relationship.class);

        return getCompositeRelationship(relationship);
    }

    private CompositeRelationship setRelationshipStatus(@NotNull UUID relID, @NotNull String status) {
        RelationshipRecord relationshipRecord = duo.update(RELATIONSHIP)
                .set(RELATIONSHIP.STATUS, status)
                .where(RELATIONSHIP.RELATIONSHIP_UUID.eq(relID))
                .returning()
                .fetchOne();

        if (relationshipRecord != null) {
            return getCompositeRelationship(relationshipRecord.getRelationshipUuid());
        } else {
            throw new NoMatchingRowException("No Relationship found matching relID='" + relID + "'");
        }
    }


    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    @Nullable
    public RelationshipRequest getRelationshipRequest(@NotNull UUID requestID) {
        return duo.selectFrom(RELATIONSHIP_REQUEST).where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_UUID.eq(requestID)).fetchOneInto(RelationshipRequest.class);
    }

    @NotNull
    public CompositeRelationshipRequest getCompositeRelationshipRequest(@NotNull UUID requestUUID) {
        RelationshipRequest relationshipRequest = getRelationshipRequest(requestUUID);

        if (relationshipRequest != null) {
            return getCompositeRelationshipRequest(relationshipRequest);
        } else {
            throw new NoMatchingRowException("No RelationshipRequest found for requestID='" + requestUUID + "'");
        }
    }

    public CompositeRelationshipRequest getCompositeRelationshipRequest(@NotNull RelationshipRequest relationshipRequest) {
        return new CompositeRelationshipRequest(relationshipRequest, userService.getUser(relationshipRequest.getRelationshipRequestSenderUserUuid()), userService.getUser(relationshipRequest.getRelationshipRequestRecipientUserUuid()));
    }

    public List<CompositeRelationshipRequest> getAllActiveCompositeRelationshipRequests(UUID userID) {
        List<RelationshipRequest> userRelationshipRequests = duo.selectFrom(RELATIONSHIP_REQUEST)
                .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(userID))
                .or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_UUID.eq(userID))
                .and(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                .fetchInto(RelationshipRequest.class);

        List<CompositeRelationshipRequest> userCompositeRelationshipRequests = new ArrayList<>();

        for (RelationshipRequest singleRelationshipRequest : userRelationshipRequests) {
            userCompositeRelationshipRequests.add(getCompositeRelationshipRequest(singleRelationshipRequest));
        }

        return userCompositeRelationshipRequests;
    }

    public CompositeRelationshipRequest createRelationshipRequest(@NotNull NewRelationshipRequest request) {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(request.senderUserID) != null) {
            throw new ConflictException("User(" + request.senderUserID + ") already in Active Relationship");
        }

        if (getActiveUserRelationship(request.recipientUserID) != null) {
            throw new ConflictException("User(" + request.recipientUserID + ") already in Active Relationship");
        }

        // Check if a request exists with similar data
        Condition sameRecipientID = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID));
        Condition sameRecipientEmail = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_EMAIL.eq(request.recipientUserEmail));
        Condition statusNotAcceptedOrRejected = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.notIn(Arrays.asList(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted, RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_rejected));

        if (duo.selectFrom(RELATIONSHIP_REQUEST).where(sameRecipientID).or(sameRecipientEmail).and(statusNotAcceptedOrRejected).fetch().size() > 0) {
            throw new ConflictException("A request already exists that is not Accepted or Rejected");
        }

        // At this point no request exists between the sender and recipient that is not in accepted or rejected state
        RelationshipRequest relationshipRequest = duo.insertInto(RELATIONSHIP_REQUEST)
                .columns(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_NAME,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_EMAIL, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_UUID,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_COMMENT, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_DESIRED_REL_STATUS, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_REL_IS_SECRET,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS)
                .values(request.senderUserID, request.recipientUserName, request.recipientUserEmail, request.recipientUserID,
                        request.requestComment, request.requestRelDesiredStatus, request.requestRelisSecret,
                        RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)
                .returning()
                .fetchOne()
                .into(RelationshipRequest.class);

        return getCompositeRelationshipRequest(relationshipRequest);
    }

    /**
     * Sets the final status of RelationshipRequest, which can only be done if the RelationshipRequest has the current
     * status of Requested.
     */
    @Transactional
    public CompositeRelationshipRequest setFinalRelationshipRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        // First we retrieve the Request
        RelationshipRequest relationshipRequest = getRelationshipRequest(requestID);

        if (relationshipRequest == null) {
            throw new NoMatchingRowException("No RelationshipRequest found matching requestID='" + requestID + "'");
        }

        // If the Request is anything other than REQUESTED, fail.
        if (!relationshipRequest.getRelationshipRequestStatus().equalsIgnoreCase(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)) {
            throw new ConflictException("RelationshipRequest has status:'" + relationshipRequest.getRelationshipRequestStatus() + "'");
        }

        // Now set the Status of the Request
        RelationshipRequestRecord relationshipRequestRecord = duo.update(RELATIONSHIP_REQUEST)
                .set(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS, status)
                .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_UUID.eq(requestID))
                .and(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                .returning()
                .fetchOne();


        // If the status is ACCEPTED, we must first create a new Relationship
        if (status.equalsIgnoreCase(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted)) {
            if (createRelationship(relationshipRequest.getRelationshipRequestSenderUserUuid(),
                    relationshipRequest.getRelationshipRequestRecipientUserUuid(),
                    relationshipRequest.getRelationshipRequestDesiredRelStatus(),
                    relationshipRequest.getRelationshipRequestRelIsSecret()) == null) {
                throw new NoMatchingRowException("No Relationship created! Error!");
            }

            // And cancel and reject all other Requests
            duo.update(RELATIONSHIP_REQUEST)
                    .set(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS, RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_cancelled)
                    .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                    .returning()
                    .fetchOne();
        }


        if (relationshipRequestRecord != null) {
            return getCompositeRelationshipRequest(relationshipRequestRecord.getRelationshipRequestUuid());
        } else {
            throw new NoMatchingRowException("No RelationshipRequest found matching requestID='" + requestID + "' having status " + RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested);
        }
    }


    /************************
     * RELATIONSHIP BREAKUP
     ************************/

    @Nullable
    public RelationshipBreakupRequest getActiveRelationshipBreakup(@NotNull UUID relationshipID) {
        return duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST)
                .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_UUID.eq(relationshipID))
                .and(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                .fetchOneInto(RelationshipBreakupRequest.class); // There should never be more than a single active breakup request
    }

    @NotNull
    public CompositeRelationshipBreakupRequest getActiveCompositeRelationshipBreakup(@NotNull UUID relationshipID) {
        RelationshipBreakupRequest activeRelationshipBreakup = getActiveRelationshipBreakup(relationshipID);

        if (activeRelationshipBreakup != null) {
            return getActiveCompositeRelationshipBreakup(activeRelationshipBreakup);
        } else {
            throw new NoMatchingRowException("No RelationshipBreakupRequest found for relationshipID='" + relationshipID + "'");
        }
    }

    public CompositeRelationshipBreakupRequest getActiveCompositeRelationshipBreakup(@NotNull RelationshipBreakupRequest req) {
        return new CompositeRelationshipBreakupRequest(req, getCompositeRelationship(req.getRelationshipUuid()), userService.getUser(req.getUserUuid()));
    }

    public CompositeRelationshipBreakupRequest requestCompositeRelationshipBreakup(@NotNull NewRelationshipBreakupRequest newBreakupRequest) {
        // First check if any other Breakup requests exist for the RelationshipID with status as PROCESSING
        if (getActiveRelationshipBreakup(newBreakupRequest.relID) != null) {
            throw new ConflictException("Relationship already has a requested breakup");
        }

        RelationshipBreakupRequest request = duo.insertInto(RELATIONSHIP_BREAKUP_REQUEST)
                .columns(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_UUID, RELATIONSHIP_BREAKUP_REQUEST.USER_UUID, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_COMMENT, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS)
                .values(newBreakupRequest.relID, newBreakupRequest.requestingUserID, newBreakupRequest.comment, RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing)
                .returning()
                .fetchOne()
                .into(RelationshipBreakupRequest.class);

        return getActiveCompositeRelationshipBreakup(request);
    }

    @Transactional
    public CompositeRelationshipBreakupRequest setFinalRelationshipBreakupRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        // First we retrieve the Request
        RelationshipBreakupRequest relbreakupRequest = duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST).where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_UUID.eq(requestID)).fetchOneInto(RelationshipBreakupRequest.class);

        if (relbreakupRequest == null) {
            throw new NoMatchingRowException("No RelbreakupRequest found matching requestID='" + requestID + "'");
        }

        // If the Request is anything other than REQUESTED, fail.
        if (!relbreakupRequest.getRelationshipBreakupRequestStatus().equalsIgnoreCase(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing)) {
            throw new ConflictException("RelbreakupRequest has status:'" + relbreakupRequest.getRelationshipBreakupRequestStatus() + "'");
        }

        // If the Status is COMPLETED, we must first set the Status of the Relationship to ENDED
        if (status.equalsIgnoreCase(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_completed)) {
            if (setRelationshipStatus(relbreakupRequest.getRelationshipUuid(), RequestParameters.RELATIONSHIP_status_ended) == null) {
                throw new NoMatchingRowException("No Relationship found! Error!");
            }
        }


        RelationshipBreakupRequestRecord relbreakupRequestRecord = duo.update(RELATIONSHIP_BREAKUP_REQUEST)
                .set(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS, status)
                .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_UUID.eq(requestID))
                .and(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                .returning()
                .fetchOne();

        if (relbreakupRequestRecord != null) {
            return getActiveCompositeRelationshipBreakup(relbreakupRequestRecord.getRelationshipUuid());
        } else {
            throw new NoMatchingRowException("No RelationshipBreakupRequest found matching requestID='" + requestID + "' having status " + RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing);
        }
    }


    /*************************
     * FULLRELATIONSHIPDATA - Custom
     *************************/

    @NotNull
    public FullRelationshipData getFullRelationshipData(@NotNull UUID relID) {
        CompositeRelationship compositeRelationship = getCompositeRelationship(relID);

        if (compositeRelationship != null) {
            return new FullRelationshipData(compositeRelationship, compositeRelationship.getUserOne(), compositeRelationship.getUserTwo(), pointService.getCompositePointEvents(compositeRelationship));
        } else {
            throw new NoMatchingRowException("No CompositeRelationship found for relID='" + relID + "'");
        }
    }

    @NotNull
    public FullRelationshipData getFullRelationshipData(@NotNull UUID relID, @NotNull UUID givingUser) {
        CompositeRelationship compositeRelationship = getCompositeRelationship(relID);

        if (compositeRelationship != null) {
            if (compositeRelationship.getUserUuid_1().equals(givingUser)) {
                return new FullRelationshipData(compositeRelationship, compositeRelationship.getUserOne(), compositeRelationship.getUserTwo(), pointService.getCompositePointEventsGivenByUserOne(compositeRelationship));
            } else if (compositeRelationship.getUserUuid_2().equals(givingUser)) {
                return new FullRelationshipData(compositeRelationship, compositeRelationship.getUserOne(), compositeRelationship.getUserTwo(), pointService.getCompositePointEventsGivenByUserTwo(compositeRelationship));
            } else {
                throw new NoMatchingRowException("No CompositeRelationship found for relID='" + relID + "' with givingUser='" + givingUser + "'");
            }
        } else {
            throw new NoMatchingRowException("No CompositeRelationship found for relID='" + relID + "'");
        }
    }
}
