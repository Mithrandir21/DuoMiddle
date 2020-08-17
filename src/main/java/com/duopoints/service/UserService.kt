package com.duopoints.service

import com.duopoints.db.tables.pojos.UserAddress
import com.duopoints.db.tables.pojos.UserLevel
import com.duopoints.db.tables.pojos.UserLevelUpLike
import com.duopoints.db.tables.pojos.Userdata
import com.duopoints.db.tables.records.UserAddressRecord
import com.duopoints.db.tables.records.UserdataRecord
import com.duopoints.errorhandling.NoMatchingRowException
import com.duopoints.models.UserFeed
import com.duopoints.models.auth.UserAuthInfo
import com.duopoints.models.auth.UserAuthWrapper
import com.duopoints.models.composites.CompositeUserLevel
import com.duopoints.models.posts.UserReg
import com.duopoints.service.fcm.FcmService
import com.google.firebase.auth.FirebaseToken
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.ArrayList
import java.util.UUID

import com.duopoints.db.tables.UserAddress.USER_ADDRESS
import com.duopoints.db.tables.UserLevel.USER_LEVEL
import com.duopoints.db.tables.UserLevelUpLike.USER_LEVEL_UP_LIKE
import com.duopoints.db.tables.Userdata.USERDATA
import org.jetbrains.annotations.NotNull
import org.jooq.impl.DSL.row

@Service
class UserService {

    @Autowired
    @Qualifier("dsl")
    lateinit var duo: DefaultDSLContext

    @Autowired
    lateinit var friendService: FriendService

    @Autowired
    lateinit var pointService: PointService

    @Autowired
    lateinit var fcmService: FcmService


    /**********
     * AUTH
     **********/

    fun authUserJWT(wrapper: UserAuthWrapper): String {
        // Decrypt and map the encrypted wrapped UserAuthInfo
        val (accessToken, userUid, userEmail) = fcmService.UserAuthInfo(wrapper)

        // Verify the accessToken for the specified User
        val firebaseToken = fcmService.verifyToken(accessToken)

        // Create a signed and encrypted JWT claim, to be used
        return fcmService.createUserJWT(firebaseToken, userUid, userEmail)
    }


    /**********
     * USER
     **********/

    fun regUser(userReg: UserReg): Userdata {
        val userdataRecord = duo
                .insertInto(USERDATA,
                        USERDATA.ADR_COUNTRY, USERDATA.ADR_CITY,
                        USERDATA.USER_AUTH_PROVIDER, USERDATA.USER_AUTH_ID,
                        USERDATA.USER_EMAIL, USERDATA.USER_FIRSTNAME, USERDATA.USER_LASTNAME, USERDATA.USER_NICKNAME,
                        USERDATA.USER_GENDER, USERDATA.USER_AGE)
                .values(userReg.country, userReg.city,
                        userReg.auth_provider, userReg.auth_id,
                        userReg.email, userReg.firstname, userReg.lastname, userReg.nickname,
                        userReg.gender.toString(), userReg.age)
                .returning()
                .fetchOne()

        return getUser(userdataRecord.userUuid)
    }

    fun getUser(userID: UUID): Userdata = duo.selectFrom(USERDATA).where(USERDATA.USER_UUID.eq(userID)).fetchOneInto(Userdata::class.java)

    @NotNull
    fun getUsers(userIDs: List<UUID>): List<Userdata> =
            duo.selectFrom(USERDATA)
                    .where(USERDATA.USER_UUID.`in`(userIDs))
                    .fetchInto(Userdata::class.java)

    @NotNull
    fun searchForUser(query: String): List<Userdata> =
            duo.selectFrom(USERDATA)
                    .where(USERDATA.USER_FIRSTNAME.like("%$query%"))
                    .or(USERDATA.USER_LASTNAME.like("%$query%"))
                    .or(USERDATA.USER_NICKNAME.like("%$query%"))
                    .limit(50)
                    .fetchInto(Userdata::class.java)


    fun getUserWithAuthID(userAuthID: String): Userdata? =
            duo.selectFrom(USERDATA).where(USERDATA.USER_AUTH_ID.eq(userAuthID)).fetchOneInto(Userdata::class.java)


    /************
     * USER ADR
     ************/

    fun updateUserAddress(newUserAddress: UserAddress): Boolean {
        val userAddressRecord = duo.update(USER_ADDRESS)
                .set(row(USER_ADDRESS.ADR_CITY, USER_ADDRESS.ADR_COUNTRY, USER_ADDRESS.ADR_REGION),
                        row(newUserAddress.adrCity, newUserAddress.adrCountry, newUserAddress.adrRegion))
                .where(USER_ADDRESS.ADDRESS_UUID.eq(newUserAddress.addressUuid))
                .returning()
                .fetchOne()

        return if (userAddressRecord != null) {
            true
        } else {
            throw NoMatchingRowException("No UserAddress found with ID='" + newUserAddress.addressUuid + "'")
        }
    }

    /**************
     * USER LEVEL
     **************/

    fun getUserLevel(userLevelID: UUID): UserLevel? =
            duo.selectFrom(USER_LEVEL).where(USER_LEVEL.USER_LEVEL_UUID.eq(userLevelID)).fetchOneInto(UserLevel::class.java)

    fun getUserLevels(userIDs: List<UUID>): List<CompositeUserLevel> {
        // Get the Users by their userIDs
        val users = getUsers(userIDs)

        // Create a List of the Users UserLevelIDs
        val userLevelIDs = ArrayList<UUID>()
        for (singleUser in users) userLevelIDs.add(singleUser.userLevelUuid)

        // Retrieve a List of all the UserLevelIDs
        val userLevels = duo.selectFrom(USER_LEVEL).where(USER_LEVEL.USER_LEVEL_UUID.`in`(userLevelIDs)).fetchInto(UserLevel::class.java)

        val compositeUserLevels = ArrayList<CompositeUserLevel>()

        // Go through both lists and match up the users with the levels
        for (singleUserLevel in userLevels) {
            for (singleUser in users) {
                if (singleUser.userLevelUuid == singleUserLevel.userLevelUuid) {
                    compositeUserLevels.add(CompositeUserLevel(singleUserLevel, singleUser))
                    break // Break out of the inner Userdata For loop
                }
            }
        }

        return compositeUserLevels
    }


    /*********************
     * LEVEL UP LIKES
     *********************/

    @NotNull
    fun likedLevelUp(userID: UUID): List<UUID> =
            duo.select(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_UUID)
                    .from(USER_LEVEL_UP_LIKE)
                    .where(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_LIKE_USER_UUID.eq(userID))
                    .fetchInto(UUID::class.java)

    fun likeLevelUp(levelUpID: UUID, userID: UUID): UserLevelUpLike =
            duo.insertInto(USER_LEVEL_UP_LIKE)
                    .columns(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_UUID, USER_LEVEL_UP_LIKE.USER_LEVEL_UP_LIKE_USER_UUID)
                    .values(levelUpID, userID)
                    .returning()
                    .fetchOne()
                    .into(UserLevelUpLike::class.java)

    fun unlikeLevelUp(levelUpID: UUID, userID: UUID): UserLevelUpLike =
            duo.deleteFrom(USER_LEVEL_UP_LIKE)
                    .where(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_UUID.eq(levelUpID))
                    .and(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_LIKE_USER_UUID.eq(userID))
                    .returning()
                    .fetchOne()
                    .into(UserLevelUpLike::class.java)


    /**********
     * FEED
     **********/

    fun getUsersFeed(userID: UUID): UserFeed {
        // First get a list of users Friends UUIDs
        val userIDs = friendService.getAllActiveFriendshipsUUIDs(userID).toMutableList()

        // Add given UUID to the list userIDs
        userIDs.add(userID)

        return UserFeed(getUserLevels(userIDs), pointService.getCompositePointEvents(userIDs))
    }
}