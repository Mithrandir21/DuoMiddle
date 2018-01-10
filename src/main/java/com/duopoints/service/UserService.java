package com.duopoints.service;

import com.duopoints.db.Routines;
import com.duopoints.db.tables.Userdata;
import com.duopoints.db.tables.pojos.User;
import com.duopoints.db.tables.pojos.UserAddress;
import com.duopoints.db.tables.records.UserAddressRecord;
import com.duopoints.db.tables.records.UserdataRecord;
import com.duopoints.errorhandling.NoMatchingRowException;
import com.duopoints.errorhandling.NullResultException;
import com.duopoints.models.posts.UserReg;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.User.USER;
import static com.duopoints.db.tables.UserAddress.USER_ADDRESS;
import static org.jooq.impl.DSL.row;

@Service
public class UserService {

    @Autowired
    @Qualifier("configuration")
    private Configuration duoConfig;

    /**********
     * USER
     **********/

    public User regUser(@NotNull UserReg userReg) {
        UserdataRecord userdataRecord = duoConfig.dsl()
                .insertInto(Userdata.USERDATA,
                        Userdata.USERDATA.ADR_COUNTRY, Userdata.USERDATA.ADR_CITY,
                        Userdata.USERDATA.USER_AUTHPROVIDER, Userdata.USERDATA.USER_AUTH_ID,
                        Userdata.USERDATA.USER_EMAIL, Userdata.USERDATA.USER_FIRSTNAME, Userdata.USERDATA.USER_LASTNAME, Userdata.USERDATA.USER_NICKNAME,
                        Userdata.USERDATA.USER_GENDER, Userdata.USERDATA.USER_AGE)
                .values(userReg.country, userReg.city,
                        userReg.auth_provider, userReg.auth_id,
                        userReg.email, userReg.firstname, userReg.lastname, userReg.nickname,
                        userReg.gender.name(), userReg.age)
                .returning()
                .fetchOne();

        return getUser(userdataRecord.getUserdbId());
    }

    public User getUser(@NotNull UUID userID) {
        return duoConfig.dsl().selectFrom(USER).where(USER.USERDB_ID.eq(userID)).fetchOneInto(User.class);
    }

    public List<User> getAllUsers() {
        return duoConfig.dsl().selectFrom(USER).fetchInto(User.class);
    }

    public int getAllUserPoint(@NotNull UUID userID) {
        return Routines.alluserpoints(duoConfig, userID);
    }


    /************
     * USER ADR
     ************/

    public UserAddress getUserAddress(@NotNull UUID adrID) {
        return duoConfig.dsl().selectFrom(USER_ADDRESS).where(USER_ADDRESS.ADDRESSDB_ID.eq(adrID)).fetchOneInto(UserAddress.class);
    }

    public UserAddress updateUserAddress(@NotNull UserAddress newUserAddress) {
        UserAddressRecord userAddressRecord = duoConfig.dsl().update(USER_ADDRESS)
                .set(row(USER_ADDRESS.ADR_CITY, USER_ADDRESS.ADR_COUNTRY, USER_ADDRESS.ADR_REGION,
                        USER_ADDRESS.ADR_USER_LASTKNOWNLOCATION_1, USER_ADDRESS.ADR_USER_LASTKNOWNLOCATION_2, USER_ADDRESS.ADR_USER_LASTKNOWNLOCATION_3),
                        row(newUserAddress.getAdrCity(), newUserAddress.getAdrCountry(), newUserAddress.getAdrRegion(),
                                newUserAddress.getAdrUserLastknownlocation_1(), newUserAddress.getAdrUserLastknownlocation_2(), newUserAddress.getAdrUserLastknownlocation_3()))
                .where(USER_ADDRESS.ADDRESSDB_ID.eq(newUserAddress.getAddressdbId()))
                .returning()
                .fetchOne();

        if( userAddressRecord != null ){
            return userAddressRecord.into(UserAddress.class);
        } else {
            throw new NoMatchingRowException("No UserAddress found with ID='" + newUserAddress.getAddressdbId() + "'");
        }
    }
}