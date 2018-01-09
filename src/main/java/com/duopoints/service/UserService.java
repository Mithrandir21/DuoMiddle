package com.duopoints.service;

import com.duopoints.db.Routines;
import com.duopoints.db.routines.Alluserpoints;
import com.duopoints.db.tables.Userdata;
import com.duopoints.db.tables.pojos.User;
import com.duopoints.db.tables.records.UserdataRecord;
import com.duopoints.errorhandling.NullResultException;
import com.duopoints.models.UserReg;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.duopoints.db.tables.User.USER;

@Service
public class UserService {

    @Autowired
    @Qualifier("configuration")
    private Configuration duoConfig;

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
        User foundUser = duoConfig.dsl().selectFrom(USER).where(USER.USERDB_ID.eq(userID)).fetchOneInto(User.class);
        if (foundUser != null) {
            return foundUser;
        } else {
            throw new NullResultException();
        }
    }

    public List<User> getAllUsers() {
        return duoConfig.dsl().selectFrom(USER).fetchInto(User.class);
    }

    public int getAllUserPoint(@NotNull UUID userID){
        return Integer.parseInt(Routines.alluserpoints(duoConfig, userID));
    }
}