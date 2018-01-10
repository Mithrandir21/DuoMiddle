package com.duopoints.service;

import com.duopoints.db.Routines;
import com.duopoints.db.tables.pojos.Relationship;
import com.duopoints.db.tables.pojos.RelationshipRequest;
import com.duopoints.db.tables.pojos.RelbreakupRequest;
import com.duopoints.db.tables.records.RelationshipAchievementListRecord;
import com.duopoints.db.tables.records.RelationshipRequestRecord;
import com.duopoints.db.tables.records.RelbreakupRequestRecord;
import com.duopoints.errorhandling.ConflictException;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.RequestParameters;
import com.duopoints.models.posts.NewRelationship;
import com.duopoints.models.posts.NewRelationshipBreakupRequest;
import com.duopoints.models.posts.NewRelationshipRequest;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.Relationship.RELATIONSHIP;
import static com.duopoints.db.tables.RelationshipAchievementList.RELATIONSHIP_ACHIEVEMENT_LIST;
import static com.duopoints.db.tables.RelationshipRequest.RELATIONSHIP_REQUEST;
import static com.duopoints.db.tables.RelbreakupRequest.RELBREAKUP_REQUEST;

@Service
public class RelationshipService {

    @Autowired
    @Qualifier("configuration")
    private Configuration duoConfig;

    /*********************
     * RELATIONSHIP
     *********************/

    public Relationship getRelationship(@NotNull UUID relID) {
        return duoConfig.dsl().selectFrom(RELATIONSHIP).where(RELATIONSHIP.RELATIONSHIPDB_ID.eq(relID)).fetchOneInto(Relationship.class);
    }

    public Relationship getActiveUserRelationship(@NotNull UUID userID) {
        return duoConfig.dsl().selectFrom(RELATIONSHIP)
                .where(RELATIONSHIP.USERDB_ID_1.eq(userID))
                .or(RELATIONSHIP.USERDB_ID_2.eq(userID))
                .and(RELATIONSHIP.STATUS.notEqual(RequestParameters.RELATIONSHIP_status_ended))
                .fetchOneInto(Relationship.class);
    }

    public UUID getRelationshipPartner(@NotNull UUID userID, @NotNull UUID relID) {
        return Routines.getrelationshipPartner(duoConfig, userID, relID);
    }

    public Relationship createRelationship(@NotNull NewRelationship newRelationship) {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(newRelationship.userOne) != null) {
            throw new ConflictException("User(" + newRelationship.userOne + ") already in Active Relationship");
        }

        if (getActiveUserRelationship(newRelationship.userTwo) != null) {
            throw new ConflictException("User(" + newRelationship.userTwo + ") already in Active Relationship");
        }

        // First Relationship Achievements List entry must be created for the new Relationship
        RelationshipAchievementListRecord relationshipAchievementListRecord = duoConfig.dsl().insertInto(RELATIONSHIP_ACHIEVEMENT_LIST).defaultValues().returning().fetchOne();

        // Create Relationship
        return duoConfig.dsl().insertInto(RELATIONSHIP)
                .columns(RELATIONSHIP.USERDB_ID_1, RELATIONSHIP.USERDB_ID_2, RELATIONSHIP.STATUS, RELATIONSHIP.ISSECRET, RELATIONSHIP.RELACHLISTDB_ID)
                .values(newRelationship.userOne, newRelationship.userTwo, newRelationship.relStatus, newRelationship.isSecret,
                        relationshipAchievementListRecord.getRelachlistdbId())
                .returning()
                .fetchOne()
                .into(Relationship.class);
    }


    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    public RelationshipRequest createRelationshipRequest(@NotNull NewRelationshipRequest request) {
        // First check if a request exists with similar data
        Condition sameRecipientID = RELATIONSHIP_REQUEST.RELREQUEST_SENDER_USERDB_ID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELREQUEST_SENDER_USERDB_ID.eq(request.senderUserID));
        Condition sameRecipientEmail = RELATIONSHIP_REQUEST.RELREQUEST_SENDER_USERDB_ID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELREQUEST_RECEPIENT_USER_EMAIL.eq(request.recipientUserEmail));
        Condition statusNotAcceptedOrRejected = RELATIONSHIP_REQUEST.RELREQUEST_STATUS.notIn(Arrays.asList(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted, RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_rejected));

        if (duoConfig.dsl().selectFrom(RELATIONSHIP_REQUEST).where(sameRecipientID).or(sameRecipientEmail).and(statusNotAcceptedOrRejected).fetch().size() > 0) {
            throw new ConflictException("A request already exists that is not Accepted or Rejected");
        }

        // At this point no request exists between the sender and recipient that is not in accepted or rejected state
        return duoConfig.dsl().insertInto(RELATIONSHIP_REQUEST)
                .columns(RELATIONSHIP_REQUEST.RELREQUEST_SENDER_USERDB_ID, RELATIONSHIP_REQUEST.RELREQUEST_RECEPIENT_USER_NAME,
                        RELATIONSHIP_REQUEST.RELREQUEST_RECEPIENT_USER_EMAIL, RELATIONSHIP_REQUEST.RELREQUEST_RECEPIENT_USERDB_ID,
                        RELATIONSHIP_REQUEST.RELREQUEST_COMMENT, RELATIONSHIP_REQUEST.RELREQUEST_STATUS)
                .values(request.senderUserID, request.recipientUserName, request.recipientUserEmail, request.recipientUserID, request.requestComment, RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)
                .returning()
                .fetchOne()
                .into(RelationshipRequest.class);
    }

    public List<RelationshipRequest> getRelationshipRequestsSentByUser(@NotNull UUID userID) {
        return duoConfig.dsl().selectFrom(RELATIONSHIP_REQUEST).where(RELATIONSHIP_REQUEST.RELREQUEST_SENDER_USERDB_ID.eq(userID)).fetchInto(RelationshipRequest.class);
    }

    /**
     * Sets the final status of RelationshipRequest, which can only be done if the RelationshipRequest has the current
     * status of Requested.
     */
    public RelationshipRequest setFinalRelationshipRequestStatus(@NotNull UUID requestID, @NotNull String status) {
        RelationshipRequestRecord relationshipRequestRecord = duoConfig.dsl().update(RELATIONSHIP_REQUEST)
                .set(RELATIONSHIP_REQUEST.RELREQUEST_STATUS, status)
                .where(RELATIONSHIP_REQUEST.RELREQUESTDB_ID.eq(requestID))
                .and(RELATIONSHIP_REQUEST.RELREQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
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

    public RelbreakupRequest getActiveRelationshipBreakup(@NotNull UUID relationshipID) {
        return duoConfig.dsl().selectFrom(RELBREAKUP_REQUEST)
                .where(RELBREAKUP_REQUEST.RELATIONSHIPDB_ID.eq(relationshipID))
                .and(RELBREAKUP_REQUEST.RELBREAKUPREQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                .fetchOneInto(RelbreakupRequest.class); // There should never be more than a single active breakup request
    }

    public RelbreakupRequest requestRelationshipBreakup(@NotNull NewRelationshipBreakupRequest newBreakupRequest) {
        // First check if any other Breakup requests exist for the RelationshipID with status as PROCESSING
        if (getActiveRelationshipBreakup(newBreakupRequest.relID) != null) {
            throw new ConflictException("Relationship already has a requested breakup");
        }

        return duoConfig.dsl().insertInto(RELBREAKUP_REQUEST)
                .columns(RELBREAKUP_REQUEST.RELATIONSHIPDB_ID, RELBREAKUP_REQUEST.USERDB_ID, RELBREAKUP_REQUEST.RELBREAKUPREQUEST_COMMENT, RELBREAKUP_REQUEST.RELBREAKUPREQUEST_STATUS)
                .values(newBreakupRequest.relID, newBreakupRequest.requestingUserID, newBreakupRequest.comment, RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing)
                .returning()
                .fetchOne()
                .into(RelbreakupRequest.class);
    }

    public RelbreakupRequest setFinalRelBreakupRequestStatus(@NotNull UUID requestID, @NotNull String status){
        RelbreakupRequestRecord relbreakupRequestRecord = duoConfig.dsl().update(RELBREAKUP_REQUEST)
                .set(RELBREAKUP_REQUEST.RELBREAKUPREQUEST_STATUS, status)
                .where(RELBREAKUP_REQUEST.RELBREAKUPREQUESTDB_ID.eq(requestID))
                .and(RELBREAKUP_REQUEST.RELBREAKUPREQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                .returning()
                .fetchOne();

        if (relbreakupRequestRecord != null) {
            return relbreakupRequestRecord.into(RelbreakupRequest.class);
        } else {
            throw new NoMatchingRowException("No RelationshipBreakupRequest found matching requestID='" + requestID + "' having status " + RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing);
        }
    }
}
