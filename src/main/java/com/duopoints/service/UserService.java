package com.duopoints.service;

import com.duopoints.db.tables.pojos.UserAddress;
import com.duopoints.db.tables.pojos.Userdata;
import com.duopoints.db.tables.records.UserAddressRecord;
import com.duopoints.db.tables.records.UserdataRecord;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.composites.CompositePointEvent;
import com.duopoints.models.posts.UserReg;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.UserAddress.USER_ADDRESS;
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
        fcmService.send();

        return duo.selectFrom(USERDATA).where(USERDATA.USER_UUID.eq(userID)).fetchOneInto(Userdata.class);
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


    /**********
     * FEED
     **********/

    public List<CompositePointEvent> getUsersFeed(@NotNull UUID userID) {
        // First get a list of users Friends UUIDs
        List<UUID> userIDs = friendService.getAllActiveFriendshipsUUIDs(userID);

        // Add given UUID to the list userIDs
        userIDs.add(userID);

        // This retrieves all the CompositePointEvents for all the Active Relationships for all the given UUIDs.
        return pointService.getCompositePointEvents(userIDs);
    }
}