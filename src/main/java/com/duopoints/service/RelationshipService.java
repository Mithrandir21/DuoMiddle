package com.duopoints.service;

import com.duopoints.db.Routines;
import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.RelationshipBreakupRequest;
import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.db.tables.records.RelationshipAchievementListRecord;
import com.duopoints.db.tables.records.RelationshipBreakupRequestRecord;
import com.duopoints.db.tables.records.RelationshipRecord;
import com.duopoints.db.tables.records.RelationshipRequestRecord;
import com.duopoints.errorhandling.ConflictException;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.RequestParameters;
import com.duopoints.models.posts.NewRelationshipBreakupRequest;
import com.duopoints.models.posts.NewRelationshipRequest;
import org.jooq.Condition;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.Relationship.RELATIONSHIP;
import static com.duopoints.db.tables.RelationshipAchievementList.RELATIONSHIP_ACHIEVEMENT_LIST;
import static com.duopoints.db.tables.RelationshipBreakupRequest.RELATIONSHIP_BREAKUP_REQUEST;
import static com.duopoints.db.tables.RelationshipRequest.RELATIONSHIP_REQUEST;

@Service
public class RelationshipService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;

    /*********************
     * RELATIONSHIP
     *********************/

    public Relationship getRelationship(@NotNull UUID relID) {
        return duo.selectFrom(RELATIONSHIP).where(RELATIONSHIP.RELATIONSHIP_UUID.eq(relID)).fetchOneInto(Relationship.class);
    }

    public Relationship getActiveUserRelationship(@NotNull UUID userID) {
        return duo.selectFrom(RELATIONSHIP)
                .where(RELATIONSHIP.USER_UUID_1.eq(userID))
                .or(RELATIONSHIP.USER_UUID_2.eq(userID))
                .and(RELATIONSHIP.STATUS.notEqual(RequestParameters.RELATIONSHIP_status_ended))
                .fetchOneInto(Relationship.class);
    }

    public UUID getRelationshipPartner(@NotNull UUID userID, @NotNull UUID relID) {
        return Routines.getrelationshipPartner(duo.configuration(), userID, relID);
    }

    private Relationship createRelationship(@NotNull UUID userOne, @NotNull UUID userTwo, @NotNull String relStatus, boolean isSecret) {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(userOne) != null) {
            throw new ConflictException("User(" + userOne + ") already in Active Relationship");
        }

        if (getActiveUserRelationship(userTwo) != null) {
            throw new ConflictException("User(" + userTwo + ") already in Active Relationship");
        }

        // First Relationship Achievements List entry must be created for the new Relationship
        RelationshipAchievementListRecord relationshipAchievementListRecord = duo.insertInto(RELATIONSHIP_ACHIEVEMENT_LIST).defaultValues().returning().fetchOne();

        // Create Relationship
        return duo.insertInto(RELATIONSHIP)
                .columns(RELATIONSHIP.USER_UUID_1, RELATIONSHIP.USER_UUID_2, RELATIONSHIP.STATUS, RELATIONSHIP.IS_SECRET, RELATIONSHIP.RELATIONSHIP_ACHIEVEMENT_LIST_UUID)
                .values(userOne, userTwo, relStatus, isSecret, relationshipAchievementListRecord.getRelationshipAchievementListUuid())
                .returning()
                .fetchOne()
                .into(Relationship.class);
    }

    private Relationship setRelationshipStatus(@NotNull UUID relID, @NotNull String status) {
        RelationshipRecord relationshipRecord = duo.update(RELATIONSHIP)
                .set(RELATIONSHIP.STATUS, status)
                .where(RELATIONSHIP.RELATIONSHIP_UUID.eq(relID))
                .returning()
                .fetchOne();

        if (relationshipRecord != null) {
            return relationshipRecord.into(Relationship.class);
        } else {
            throw new NoMatchingRowException("No Relationship found matching relID='" + relID + "'");
        }
    }


    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    public RelationshipRequest createRelationshipRequest(@NotNull NewRelationshipRequest request) {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(request.senderUserID) != null) {
            throw new ConflictException("User(" + request.senderUserID + ") already in Active Relationship");
        }

        if (getActiveUserRelationship(request.recipientUserID) != null) {
            throw new ConflictException("User(" + request.recipientUserID + ") already in Active Relationship");
        }

        // Check if a request exists with similar data
        Condition sameRecipientID = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID));
        Condition sameRecipientEmail = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECEPIENT_USER_EMAIL.eq(request.recipientUserEmail));
        Condition statusNotAcceptedOrRejected = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.notIn(Arrays.asList(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted, RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_rejected));

        if (duo.selectFrom(RELATIONSHIP_REQUEST).where(sameRecipientID).or(sameRecipientEmail).and(statusNotAcceptedOrRejected).fetch().size() > 0) {
            throw new ConflictException("A request already exists that is not Accepted or Rejected");
        }

        // At this point no request exists between the sender and recipient that is not in accepted or rejected state
        return duo.insertInto(RELATIONSHIP_REQUEST)
                .columns(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECEPIENT_USER_NAME,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECEPIENT_USER_EMAIL, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECEPIENT_USER_UUID,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_COMMENT, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_DESIRED_REL_STATUS, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_REL_IS_SECRET,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS)
                .values(request.senderUserID, request.recipientUserName, request.recipientUserEmail, request.recipientUserID,
                        request.requestComment, request.requestRelDesiredStatus, request.requestRelisSecret,
                        RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)
                .returning()
                .fetchOne()
                .into(RelationshipRequest.class);
    }

    public List<RelationshipRequest> getRelationshipRequestsSentByUser(@NotNull UUID userID) {
        return duo.selectFrom(RELATIONSHIP_REQUEST).where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(userID)).fetchInto(RelationshipRequest.class);
    }

    /**
     * Sets the final status of RelationshipRequest, which can only be done if the RelationshipRequest has the current
     * status of Requested.
     */
    @Transactional
    public RelationshipRequest setFinalRelationshipRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        // First we retrieve the Request
        RelationshipRequest relationshipRequest = duo.selectFrom(RELATIONSHIP_REQUEST)
                .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_UUID.eq(requestID)).fetchOneInto(RelationshipRequest.class);

        if (relationshipRequest == null) {
            throw new NoMatchingRowException("No RelationshipRequest found matching requestID='" + requestID + "'");
        }

        // If the Request is anything other than REQUESTED, fail.
        if (!relationshipRequest.getRelationshipRequestStatus().equalsIgnoreCase(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)) {
            throw new ConflictException("RelationshipRequest has status:'" + relationshipRequest.getRelationshipRequestStatus() + "'");
        }

        // If the status is ACCEPTED, we must first create a new Relationship
        if (status.equalsIgnoreCase(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted)) {
            if (createRelationship(relationshipRequest.getRelationshipRequestSenderUserUuid(),
                    relationshipRequest.getRelationshipRequestRecepientUserUuid(),
                    relationshipRequest.getRelationshipRequestDesiredRelStatus(),
                    relationshipRequest.getRelationshipRequestRelIsSecret()) == null) {
                throw new NoMatchingRowException("No Relationship created! Error!");
            }
        }


        // Now set the Status of the Request
        RelationshipRequestRecord relationshipRequestRecord = duo.update(RELATIONSHIP_REQUEST)
                .set(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS, status)
                .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_UUID.eq(requestID))
                .and(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                .returning()
                .fetchOne();

        if (relationshipRequestRecord != null) {
            return relationshipRequestRecord.into(RelationshipRequest.class);
        } else {
            throw new NoMatchingRowException("No RelationshipRequest found matching requestID='" + requestID + "' having status " + RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested);
        }
    }


    /************************
     * RELATIONSHIP BREAKUP
     ************************/

    public RelationshipBreakupRequest getActiveRelationshipBreakup(@NotNull UUID relationshipID) {
        return duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST)
                .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_UUID.eq(relationshipID))
                .and(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                .fetchOneInto(RelationshipBreakupRequest.class); // There should never be more than a single active breakup request
    }

    public RelationshipBreakupRequest requestRelationshipBreakup(@NotNull NewRelationshipBreakupRequest newBreakupRequest) {
        // First check if any other Breakup requests exist for the RelationshipID with status as PROCESSING
        if (getActiveRelationshipBreakup(newBreakupRequest.relID) != null) {
            throw new ConflictException("Relationship already has a requested breakup");
        }

        return duo.insertInto(RELATIONSHIP_BREAKUP_REQUEST)
                .columns(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_UUID, RELATIONSHIP_BREAKUP_REQUEST.USER_UUID, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_COMMENT, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS)
                .values(newBreakupRequest.relID, newBreakupRequest.requestingUserID, newBreakupRequest.comment, RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing)
                .returning()
                .fetchOne()
                .into(RelationshipBreakupRequest.class);
    }

    @Transactional
    public RelationshipBreakupRequest setFinalRelationshipBreakupRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        // First we retrieve the Request
        RelationshipBreakupRequest relbreakupRequest = duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST)
                .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_UUID.eq(requestID)).fetchOneInto(RelationshipBreakupRequest.class);

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
            return relbreakupRequestRecord.into(RelationshipBreakupRequest.class);
        } else {
            throw new NoMatchingRowException("No RelationshipBreakupRequest found matching requestID='" + requestID + "' having status " + RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing);
        }
    }
}
