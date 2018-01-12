package com.duopoints.service;

import com.duopoints.db.Routines;
import com.duopoints.db.tables.Userdata;
import com.duopoints.db.tables.pojos.UserAddress;
import com.duopoints.db.tables.pojos.UserLevel;
import com.duopoints.db.tables.records.UserAddressRecord;
import com.duopoints.db.tables.records.UserdataRecord;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.models.posts.UserReg;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static com.duopoints.db.tables.User.USER;
import static com.duopoints.db.tables.UserAddress.USER_ADDRESS;
import static com.duopoints.db.tables.UserLevel.USER_LEVEL;
import static com.duopoints.db.tables.Userdata.USERDATA;
import static org.jooq.impl.DSL.row;

@Service
public class UserService {

    @Autowired
    @Qualifier("dsl")
    private DefaultDSLContext duo;

    /**********
     * USER
     **********/

    public Userdata regUser(@NotNull UserReg userReg) {
        UserdataRecord userdataRecord = duo
                .insertInto(USERDATA,
                        USERDATA.ADR_COUNTRY, USERDATA.ADR_CITY,
                        USERDATA.USER_AUTH_PROVIDER, USERDATA.USER_AUTH_UUID,
                        USERDATA.USER_EMAIL, USERDATA.USER_FIRSTNAME, USERDATA.USER_LASTNAME, USERDATA.USER_NICKNAME,
                        USERDATA.USER_GENDER, USERDATA.USER_AGE)
                .values(userReg.country, userReg.city,
                        userReg.auth_provider, userReg.auth_id,
                        userReg.email, userReg.firstname, userReg.lastname, userReg.nickname,
                        userReg.gender.name(), userReg.age)
                .returning()
                .fetchOne();

        return getUser(userdataRecord.getUserUuid());
    }

    public Userdata getUser(@NotNull UUID userID) {
        return duo.selectFrom(USERDATA).where(USER.USER_UUID.eq(userID)).fetchOneInto(Userdata.class);
    }

    public int getAllUserPoint(@NotNull UUID userID) {
        return Routines.alluserpoints(duo.configuration(), userID);
    }


    /************
     * USER ADR
     ************/

    public UserAddress getUserAddress(@NotNull UUID adrID) {
        return duo.selectFrom(USER_ADDRESS).where(USER_ADDRESS.ADDRESS_UUID.eq(adrID)).fetchOneInto(UserAddress.class);
    }

    public UserAddress updateUserAddress(@NotNull UserAddress newUserAddress) {
        UserAddressRecord userAddressRecord = duo.update(USER_ADDRESS)
                .set(row(USER_ADDRESS.ADR_CITY, USER_ADDRESS.ADR_COUNTRY, USER_ADDRESS.ADR_REGION),
                        row(newUserAddress.getAdrCity(), newUserAddress.getAdrCountry(), newUserAddress.getAdrRegion()))
                .where(USER_ADDRESS.ADDRESS_UUID.eq(newUserAddress.getAddressUuid()))
                .returning()
                .fetchOne();

        if (userAddressRecord != null) {
            return userAddressRecord.into(UserAddress.class);
        } else {
            throw new NoMatchingRowException("No UserAddress found with ID='" + newUserAddress.getAddressUuid() + "'");
        }
    }


    /**************
     * USER LEVEL
     **************/

    public UserLevel getUserLevel(@NotNull UUID userLevelID) {
        return duo.selectFrom(USER_LEVEL).where(USER_LEVEL.USER_LEVEL_UUID.eq(userLevelID)).fetchOneInto(UserLevel.class);
    }
}