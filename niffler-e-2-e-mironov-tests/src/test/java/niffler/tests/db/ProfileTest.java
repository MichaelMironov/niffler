package niffler.tests.db;

import io.qameta.allure.AllureId;
import niffler.database.dao.PostgresJdbcUsersDAO;
import niffler.database.dao.PostgresSpringJdbcUsersDAO;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.jupiter.di.dao.DAO;
import niffler.jupiter.di.dao.DAOResolver;
import niffler.jupiter.di.profile.EntityGenerator;
import niffler.jupiter.di.profile.Profile;
import niffler.tests.ui.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.jupiter.di.dao.DAOType.JDBC;
import static niffler.jupiter.di.dao.DAOType.SPRING;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({DAOResolver.class, EntityGenerator.class})
public class ProfileTest extends BaseTest {

    @DAO(SPRING)
    PostgresSpringJdbcUsersDAO springJdbcUsersDAO;

    @DAO(JDBC)
    PostgresJdbcUsersDAO jdbcUsersDAO;

    @AllureId("1")
    @Test
    void jdbcAddProfilesTest(@Profile(username = "mike102", firstname = "michael", currency = "KZT") ProfileEntity profile1,
                             @Profile(username = "dima102", firstname = "dmitriy", currency = "RUB") ProfileEntity profile2) {

        jdbcUsersDAO.addUsers(profile1, profile2);

        assertAll(
                () -> assertEquals(profile1.getUsername(), springJdbcUsersDAO.getByUsername(profile1.getUsername()).getUsername()),
                () -> assertEquals(profile2.getUsername(), springJdbcUsersDAO.getByUsername(profile2.getUsername()).getUsername())
        );
    }

    @AllureId("2")
    @Test
    void jdbcUpdateProfilesTest(@Profile(username = "mike103", firstname = "michael", currency = "KZT") ProfileEntity profile1,
                                @Profile(username = "dima103", firstname = "dmitriy", currency = "RUB") ProfileEntity profile2) {

        jdbcUsersDAO.addUsers(profile1, profile2);
        profile1.setFirstname("test");
        profile2.setCurrency("USD");
        jdbcUsersDAO.updateUsers(profile1, profile2);

        assertAll(
                () -> assertEquals("test", springJdbcUsersDAO.getByUsername(profile1.getUsername()).getFirstname()),
                () -> assertEquals("USD", springJdbcUsersDAO.getByUsername(profile2.getUsername()).getCurrency())
        );
    }

    @AllureId("3")
    @Test
    void springAddProfilesTest(@Profile(username = "mike104", firstname = "michael", currency = "KZT") ProfileEntity profile1,
                               @Profile(username = "dima104", firstname = "dmitriy", currency = "RUB") ProfileEntity profile2) {

        springJdbcUsersDAO.addUsers(profile1, profile2);

        assertAll(
                () -> assertEquals(profile1.getUsername(), springJdbcUsersDAO.getByUsername(profile1.getUsername()).getUsername()),
                () -> assertEquals(profile2.getUsername(), springJdbcUsersDAO.getByUsername(profile2.getUsername()).getUsername())
        );
    }

    @AllureId("4")
    @Test
    void springUpdateProfilesTest(@Profile(username = "mike105", firstname = "michael", currency = "KZT") ProfileEntity profile1,
                                  @Profile(username = "dima105", firstname = "dmitriy", currency = "RUB") ProfileEntity profile2) {

        springJdbcUsersDAO.addUsers(profile1, profile2);
        profile1.setFirstname("test");
        profile2.setCurrency("USD");
        springJdbcUsersDAO.updateUsers(profile1, profile2);

        assertAll(
                () -> assertEquals("test", springJdbcUsersDAO.getByUsername(profile1.getUsername()).getFirstname()),
                () -> assertEquals("USD", springJdbcUsersDAO.getByUsername(profile2.getUsername()).getCurrency()));
    }

}
