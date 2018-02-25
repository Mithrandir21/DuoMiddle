package com.duopoints.service;

import com.duopoints.db.tables.pojos.UserAddress;
import com.duopoints.db.tables.pojos.UserLevel;
import com.duopoints.db.tables.pojos.UserLevelUpLike;
import com.duopoints.db.tables.pojos.Userdata;
import com.duopoints.db.tables.records.UserAddressRecord;
import com.duopoints.db.tables.records.UserdataRecord;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.UserFeed;
import com.duopoints.models.composites.CompositeUserLevel;
import com.duopoints.models.posts.UserReg;
import com.duopoints.service.fcm.FcmService;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.UserAddress.USER_ADDRESS;
import static com.duopoints.db.tables.UserLevel.USER_LEVEL;
import static com.duopoints.db.tables.UserLevelUpLike.USER_LEVEL_UP_LIKE;
import static com.duopoints.db.tables.Userdata.USERDATA;
import static org.jooq.impl.DSL.row;

@Service
public class UserService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;

    @Autowired
    private FriendService friendService;

    @Autowired
    private PointService pointService;

    @Autowired
    private FcmService fcmService;


    /**********
     * USER
     **********/

    public Userdata regUser(@NotNull UserReg userReg) {
        UserdataRecord userdataRecord = duo
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
                .fetchOne();

        return getUser(userdataRecord.getUserUuid());
    }

    @Nullable
    public Userdata getUser(@NotNull UUID userID) {
        return duo.selectFrom(USERDATA).where(USERDATA.USER_UUID.eq(userID)).fetchOneInto(Userdata.class);
    }

    @NotNull
    public List<Userdata> getUsers(@NotNull List<UUID> userIDs) {
        return duo.selectFrom(USERDATA)
                .where(USERDATA.USER_UUID.in(userIDs))
                .fetchInto(Userdata.class);
    }

    @NotNull
    public List<Userdata> searchForUser(@NotNull String query) {
        return duo.selectFrom(USERDATA)
                .where(USERDATA.USER_FIRSTNAME.like("%" + query + "%"))
                .or(USERDATA.USER_LASTNAME.like("%" + query + "%"))
                .or(USERDATA.USER_NICKNAME.like("%" + query + "%"))
                .limit(50)
                .fetchInto(Userdata.class);
    }


    @Nullable
    public Userdata getUserWithAuthID(@NotNull String userAuthID) {
        return duo.selectFrom(USERDATA).where(USERDATA.USER_AUTH_ID.eq(userAuthID)).fetchOneInto(Userdata.class);
    }


    /************
     * USER ADR
     ************/

    public boolean updateUserAddress(@NotNull UserAddress newUserAddress) {
        UserAddressRecord userAddressRecord = duo.update(USER_ADDRESS)
                .set(row(USER_ADDRESS.ADR_CITY, USER_ADDRESS.ADR_COUNTRY, USER_ADDRESS.ADR_REGION),
                        row(newUserAddress.getAdrCity(), newUserAddress.getAdrCountry(), newUserAddress.getAdrRegion()))
                .where(USER_ADDRESS.ADDRESS_UUID.eq(newUserAddress.getAddressUuid()))
                .returning()
                .fetchOne();

        if (userAddressRecord != null) {
            return true;
        } else {
            throw new NoMatchingRowException("No UserAddress found with ID='" + newUserAddress.getAddressUuid() + "'");
        }
    }

    /**************
     * USER LEVEL
     **************/

    @Nullable
    public UserLevel getUserLevel(@NotNull UUID userLevelID) {
        return duo.selectFrom(USER_LEVEL).where(USER_LEVEL.USER_LEVEL_UUID.eq(userLevelID)).fetchOneInto(UserLevel.class);
    }

    @Nullable
    public List<CompositeUserLevel> getUserLevels(@NotNull List<UUID> userIDs) {
        // Get the Users by their userIDs
        List<Userdata> users = getUsers(userIDs);

        // Create a List of the Users UserLevelIDs
        List<UUID> userLevelIDs = new ArrayList<>();
        for (Userdata singleUser : users) userLevelIDs.add(singleUser.getUserLevelUuid());

        // Retrieve a List of all the UserLevelIDs
        List<UserLevel> userLevels = duo.selectFrom(USER_LEVEL).where(USER_LEVEL.USER_LEVEL_UUID.in(userLevelIDs)).fetchInto(UserLevel.class);

        List<CompositeUserLevel> compositeUserLevels = new ArrayList<>();

        // Go through both lists and match up the users with the levels
        for (UserLevel singleUserLevel : userLevels) {
            for (Userdata singleUser : users) {
                if (singleUser.getUserLevelUuid().equals(singleUserLevel.getUserLevelUuid())) {
                    compositeUserLevels.add(new CompositeUserLevel(singleUserLevel, singleUser));
                    break; // Break out of the inner Userdata For loop
                }
            }
        }

        return compositeUserLevels;
    }


    /*********************
     * LEVEL UP LIKES
     *********************/

    @NotNull
    public List<UUID> likedLevelUp(@NotNull UUID userID) {
        return duo.select(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_UUID)
                .from(USER_LEVEL_UP_LIKE)
                .where(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_LIKE_USER_UUID.eq(userID))
                .fetchInto(UUID.class);
    }

    public UserLevelUpLike likeLevelUp(@NotNull UUID levelUpID, @NotNull UUID userID) {
        return duo.insertInto(USER_LEVEL_UP_LIKE)
                .columns(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_UUID, USER_LEVEL_UP_LIKE.USER_LEVEL_UP_LIKE_USER_UUID)
                .values(levelUpID, userID)
                .returning()
                .fetchOne()
                .into(UserLevelUpLike.class);
    }

    public UserLevelUpLike unlikeLevelUp(@NotNull UUID levelUpID, @NotNull UUID userID) {
        return duo.deleteFrom(USER_LEVEL_UP_LIKE)
                .where(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_UUID.eq(levelUpID))
                .and(USER_LEVEL_UP_LIKE.USER_LEVEL_UP_LIKE_USER_UUID.eq(userID))
                .returning()
                .fetchOne()
                .into(UserLevelUpLike.class);
    }


    /**********
     * FEED
     **********/

    public UserFeed getUsersFeed(@NotNull UUID userID) {
        // First get a list of users Friends UUIDs
        List<UUID> userIDs = friendService.getAllActiveFriendshipsUUIDs(userID);

        // Add given UUID to the list userIDs
        userIDs.add(userID);

        return new UserFeed(getUserLevels(userIDs), pointService.getCompositePointEvents(userIDs));
    }
}