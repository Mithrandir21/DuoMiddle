package com.duopoints.service

import com.duopoints.RequestParameters
import com.duopoints.db.tables.pojos.Relationship
import com.duopoints.db.tables.pojos.RelationshipBreakupRequest
import com.duopoints.db.tables.pojos.RelationshipRequest
import com.duopoints.db.tables.records.RelationshipBreakupRequestRecord
import com.duopoints.db.tables.records.RelationshipRecord
import com.duopoints.db.tables.records.RelationshipRequestRecord
import com.duopoints.errorhandling.ConflictException
import com.duopoints.errorhandling.NoMatchingRowException
import com.duopoints.models.FullRelationshipData
import com.duopoints.models.composites.CompositeRelationship
import com.duopoints.models.composites.CompositeRelationshipBreakupRequest
import com.duopoints.models.composites.CompositeRelationshipRequest
import com.duopoints.models.posts.NewRelationshipBreakupRequest
import com.duopoints.models.posts.NewRelationshipRequest
import org.jooq.Condition
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.validation.constraints.NotNull
import java.sql.Timestamp
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.ArrayList
import java.util.Arrays
import java.util.UUID

import com.duopoints.db.tables.Relationship.RELATIONSHIP
import com.duopoints.db.tables.RelationshipBreakupRequest.RELATIONSHIP_BREAKUP_REQUEST
import com.duopoints.db.tables.RelationshipRequest.RELATIONSHIP_REQUEST

@Service
class RelationshipService {

    @Autowired
    @Qualifier("dsl")
    lateinit var duo: DefaultDSLContext

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var pointService: PointService


    /*********************
     * RELATIONSHIP
     *********************/

    fun getRelationship(relID: UUID): Relationship? =
            duo.selectFrom(RELATIONSHIP).where(RELATIONSHIP.RELATIONSHIP_UUID.eq(relID)).fetchOneInto(Relationship::class.java)

    fun getActiveUserRelationship(userID: UUID): Relationship? = duo.selectFrom(RELATIONSHIP)
            .where(RELATIONSHIP.USER_UUID_1.eq(userID))
            .or(RELATIONSHIP.USER_UUID_2.eq(userID))
            .and(RELATIONSHIP.STATUS.notEqual(RequestParameters.RELATIONSHIP_status_ended))
            .fetchOneInto(Relationship::class.java)

    fun getActiveUsersRelationship(userIDs: List<UUID>): List<Relationship> = duo.selectFrom(RELATIONSHIP)
            .where(RELATIONSHIP.USER_UUID_1.`in`(userIDs))
            .or(RELATIONSHIP.USER_UUID_2.`in`(userIDs))
            .and(RELATIONSHIP.STATUS.notEqual(RequestParameters.RELATIONSHIP_status_ended))
            .fetchInto(Relationship::class.java)

    fun getCompositeRelationship(relID: UUID): CompositeRelationship =
            getRelationship(relID)?.let { getCompositeRelationship(it) } ?: throw NoMatchingRowException("No Relationship found matching relID='$relID'")

    // Complete the composite object with Users needed
    fun getCompositeRelationship(relationship: Relationship): CompositeRelationship =
            CompositeRelationship(relationship, userService.getUser(relationship.userUuid_1), userService.getUser(relationship.userUuid_2))

    fun getActiveUserCompositeRelationship(userID: UUID): CompositeRelationship =
            getActiveUserRelationship(userID)?.let { getCompositeRelationship(it) }
                    ?: throw NoMatchingRowException("No Active Relationship found for userID='$userID'")

    private fun createRelationship(userOne: UUID, userTwo: UUID, relStatus: String, isSecret: Boolean): CompositeRelationship? {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(userOne) != null) {
            throw ConflictException("User($userOne) already in Active Relationship")
        }

        if (getActiveUserRelationship(userTwo) != null) {
            throw ConflictException("User($userTwo) already in Active Relationship")
        }

        // Create Relationship
        val relationship = duo.insertInto(RELATIONSHIP)
                .columns(RELATIONSHIP.USER_UUID_1, RELATIONSHIP.USER_UUID_2, RELATIONSHIP.STATUS, RELATIONSHIP.IS_SECRET)
                .values(userOne, userTwo, relStatus, isSecret)
                .returning()
                .fetchOne()
                .into(Relationship::class.java)

        return getCompositeRelationship(relationship)
    }

    private fun setRelationshipStatus(relID: UUID, status: String): CompositeRelationship? {
        val relationshipRecord = duo.update(RELATIONSHIP)
                .set(RELATIONSHIP.STATUS, status)
                .where(RELATIONSHIP.RELATIONSHIP_UUID.eq(relID))
                .returning()
                .fetchOne()

        return if (relationshipRecord != null) {
            getCompositeRelationship(relationshipRecord.relationshipUuid)
        } else {
            throw NoMatchingRowException("No Relationship found matching relID='$relID'")
        }
    }


    /*************************
     * RELATIONSHIP REQUESTS
     *************************/

    fun getRelationshipRequest(requestID: UUID): RelationshipRequest? =
            duo.selectFrom(RELATIONSHIP_REQUEST).where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_UUID.eq(requestID)).fetchOneInto(RelationshipRequest::class.java)

    fun getCompositeRelationshipRequest(requestUUID: UUID): CompositeRelationshipRequest =
            getRelationshipRequest(requestUUID)?.let { getCompositeRelationshipRequest(it) }
                    ?: throw NoMatchingRowException("No RelationshipRequest found for requestID='$requestUUID'")

    fun getCompositeRelationshipRequest(relationshipRequest: RelationshipRequest): CompositeRelationshipRequest =
            CompositeRelationshipRequest(relationshipRequest, userService.getUser(relationshipRequest.relationshipRequestSenderUserUuid), userService.getUser(relationshipRequest.relationshipRequestRecipientUserUuid))

    fun getAllActiveCompositeRelationshipRequests(userID: UUID): List<CompositeRelationshipRequest> {
        val userRelationshipRequests = duo.selectFrom(RELATIONSHIP_REQUEST)
                .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(userID))
                .or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_UUID.eq(userID))
                .and(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                .fetchInto(RelationshipRequest::class.java)

        val userCompositeRelationshipRequests = ArrayList<CompositeRelationshipRequest>()

        for (singleRelationshipRequest in userRelationshipRequests) {
            userCompositeRelationshipRequests.add(getCompositeRelationshipRequest(singleRelationshipRequest))
        }

        return userCompositeRelationshipRequests
    }

    fun createRelationshipRequest(request: NewRelationshipRequest): CompositeRelationshipRequest {
        // First check if either user is in Active Relationships
        if (getActiveUserRelationship(request.senderUserID) != null) {
            throw ConflictException("User(" + request.senderUserID + ") already in Active Relationship")
        }

        if (getActiveUserRelationship(request.recipientUserID) != null) {
            throw ConflictException("User(" + request.recipientUserID + ") already in Active Relationship")
        }

        // Check if a request exists with similar data
        val sameRecipientID = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID))
        val sameRecipientEmail = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID.eq(request.senderUserID).or(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_EMAIL.eq(request.recipientUserEmail))
        val statusIsRequested = RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.`in`(listOf(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))

        if (duo.selectFrom(RELATIONSHIP_REQUEST).where(sameRecipientID).or(sameRecipientEmail).and(statusIsRequested).fetch().size > 0) {
            throw ConflictException("A request already exists that is Requested")
        }

        // At this point no request exists between the sender and recipient that is not in accepted or rejected state
        val relationshipRequest = duo.insertInto(RELATIONSHIP_REQUEST)
                .columns(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_SENDER_USER_UUID, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_NAME,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_EMAIL, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_RECIPIENT_USER_UUID,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_COMMENT, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_DESIRED_REL_STATUS, RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_REL_IS_SECRET,
                        RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS)
                .values(request.senderUserID, request.recipientUserName, request.recipientUserEmail, request.recipientUserID,
                        request.requestComment, request.requestRelDesiredStatus, request.isRequestRelIsSecret,
                        RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)
                .returning()
                .fetchOne()
                .into(RelationshipRequest::class.java)

        return getCompositeRelationshipRequest(relationshipRequest)
    }

    /** Sets the final status of RelationshipRequest, which can only be done if the RelationshipRequest has the current status of Requested. */
    @Transactional
    fun setFinalRelationshipRequestStatus(requestID: UUID, status: String): CompositeRelationshipRequest {
        // First we retrieve the Request
        val relationshipRequest = getRelationshipRequest(requestID)
                ?: throw NoMatchingRowException("No RelationshipRequest found matching requestID='$requestID'")

        // If the Request is anything other than REQUESTED, fail.
        if (!relationshipRequest.relationshipRequestStatus.equals(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested, ignoreCase = true)) {
            throw ConflictException("RelationshipRequest has status:'" + relationshipRequest.relationshipRequestStatus + "'")
        }

        // Now set the Status of the Request
        val relationshipRequestRecord = duo.update(RELATIONSHIP_REQUEST)
                .set(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS, status)
                .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_UUID.eq(requestID))
                .and(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                .returning()
                .fetchOne()


        // If the status is ACCEPTED, we must first create a new Relationship
        if (status.equals(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_accepted, ignoreCase = true)) {
            if (createRelationship(relationshipRequest.relationshipRequestSenderUserUuid,
                            relationshipRequest.relationshipRequestRecipientUserUuid,
                            relationshipRequest.relationshipRequestDesiredRelStatus,
                            relationshipRequest.relationshipRequestRelIsSecret) == null) {
                throw NoMatchingRowException("No Relationship created! Error!")
            }

            // And cancel and reject all other Requests
            duo.update(RELATIONSHIP_REQUEST)
                    .set(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS, RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_cancelled)
                    .where(RELATIONSHIP_REQUEST.RELATIONSHIP_REQUEST_STATUS.eq(RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested))
                    .returning()
                    .fetchOne()
        }


        return if (relationshipRequestRecord != null) {
            getCompositeRelationshipRequest(relationshipRequestRecord.relationshipRequestUuid)
        } else {
            throw NoMatchingRowException("No RelationshipRequest found matching requestID='" + requestID + "' having status " + RequestParameters.RELATIONSHIP_REQUEST_rel_request_status_requested)
        }
    }


    /************************
     * RELATIONSHIP BREAKUP
     ************************/

    fun getRelationshipBreakup(requestID: UUID): RelationshipBreakupRequest? =
            duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST)
                    .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_UUID.eq(requestID))
                    .fetchOneInto(RelationshipBreakupRequest::class.java)

    fun getActiveRelationshipBreakup(relationshipID: UUID): RelationshipBreakupRequest? =
            duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST)
                    .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_UUID.eq(relationshipID))
                    .and(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                    .fetchOneInto(RelationshipBreakupRequest::class.java) // There should never be more than a single active breakup request


    fun getCompositeRelationshipBreakup(requestID: UUID): CompositeRelationshipBreakupRequest =
            getRelationshipBreakup(requestID)?.let { getCompositeRelationshipBreakup(it) }
                    ?: throw NoMatchingRowException("No RelationshipBreakupRequest found for requestID='$requestID'")


    fun getActiveCompositeRelationshipBreakup(relationshipID: UUID): CompositeRelationshipBreakupRequest =
            getActiveRelationshipBreakup(relationshipID)?.let { getCompositeRelationshipBreakup(it) }
                    ?: throw NoMatchingRowException("No RelationshipBreakupRequest found for relationshipID='$relationshipID'")


    fun getCompositeRelationshipBreakup(req: RelationshipBreakupRequest): CompositeRelationshipBreakupRequest =
            CompositeRelationshipBreakupRequest(req, getCompositeRelationship(req.relationshipUuid), userService.getUser(req.userUuid))

    fun requestCompositeRelationshipBreakup(newBreakupRequest: NewRelationshipBreakupRequest): CompositeRelationshipBreakupRequest {
        // First check if any other Breakup requests exist for the RelationshipID with status as PROCESSING
        if (getActiveRelationshipBreakup(newBreakupRequest.relationshipUUID) != null) {
            throw ConflictException("Relationship already has a requested breakup")
        }


        val waitUntil = Timestamp.from(ZonedDateTime.now(ZoneId.of("UTC")).plus(2, ChronoUnit.DAYS).toInstant())

        val request = duo.insertInto(RELATIONSHIP_BREAKUP_REQUEST)
                .columns(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_UUID, RELATIONSHIP_BREAKUP_REQUEST.USER_UUID, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_COMMENT, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS, RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_WAIT_UNTIL)
                .values(newBreakupRequest.relationshipUUID, newBreakupRequest.requestingUserUUID, newBreakupRequest.requestComment, RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing, waitUntil)
                .returning()
                .fetchOne()
                .into(RelationshipBreakupRequest::class.java)

        return getCompositeRelationshipBreakup(request)
    }

    @Transactional
    fun setFinalRelationshipBreakupRequestStatus(requestID: UUID, status: String): CompositeRelationshipBreakupRequest {
        // First we retrieve the Request
        val breakupRequest = duo.selectFrom(RELATIONSHIP_BREAKUP_REQUEST)
                .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_UUID.eq(requestID))
                .fetchOneInto(RelationshipBreakupRequest::class.java)
                ?: throw NoMatchingRowException("No RelbreakupRequest found matching requestID='$requestID'")

        // If the Request is anything other than REQUESTED, fail.
        if (!breakupRequest.relationshipBreakupRequestStatus.equals(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing, ignoreCase = true)) {
            throw ConflictException("RelbreakupRequest has status:'" + breakupRequest.relationshipBreakupRequestStatus + "'")
        }

        // If the Status is COMPLETED, we must first set the Status of the Relationship to ENDED
        if (status.equals(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_completed, ignoreCase = true)) {
            if (setRelationshipStatus(breakupRequest.relationshipUuid, RequestParameters.RELATIONSHIP_status_ended) == null) {
                throw NoMatchingRowException("No Relationship found! Error!")
            }
        }


        val relbreakupRequestRecord = duo.update(RELATIONSHIP_BREAKUP_REQUEST)
                .set(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS, status)
                .where(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_UUID.eq(requestID))
                .and(RELATIONSHIP_BREAKUP_REQUEST.RELATIONSHIP_BREAKUP_REQUEST_STATUS.eq(RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing))
                .returning()
                .fetchOne()

        return if (relbreakupRequestRecord != null) {
            getCompositeRelationshipBreakup(relbreakupRequestRecord.relationshipBreakupRequestUuid)
        } else {
            throw NoMatchingRowException("No RelationshipBreakupRequest found matching requestID='" + requestID + "' having status " + RequestParameters.REL_BREAKUP_REQUEST_rel_breakup_request_status_processing)
        }
    }


    /*************************
     * FULLRELATIONSHIPDATA - Custom
     *************************/

    fun getFullRelationshipData(relID: UUID): FullRelationshipData =
            getCompositeRelationship(relID).let { FullRelationshipData(it, it.userOne, it.userTwo, pointService.getCompositePointEvents(it)) }

    fun getFullRelationshipData(relID: UUID, givingUser: UUID): FullRelationshipData =
            getCompositeRelationship(relID).let {
                when (givingUser) {
                    it.userUuid_1 -> FullRelationshipData(it, it.userOne, it.userTwo, pointService.getCompositePointEventsGivenByUserOne(it))
                    it.userUuid_2 -> FullRelationshipData(it, it.userOne, it.userTwo, pointService.getCompositePointEventsGivenByUserTwo(it))
                    else -> throw NoMatchingRowException("No CompositeRelationship found for relID='$relID' with givingUser='$givingUser'")
                }
            }
}
