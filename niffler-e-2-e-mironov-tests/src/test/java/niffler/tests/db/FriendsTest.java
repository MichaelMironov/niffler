package niffler.tests.db;

import io.qameta.allure.AllureId;
import niffler.database.dao.PostgresJdbcUsersDAO;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.jupiter.di.dao.DAO;
import niffler.jupiter.di.dao.DAOResolver;
import niffler.jupiter.di.profile.EntityGenerator;
import niffler.jupiter.di.profile.Profile;
import niffler.tests.ui.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.util.List.of;
import static niffler.jupiter.di.dao.DAOType.JDBC;

@ExtendWith({DAOResolver.class, EntityGenerator.class})
public class FriendsTest extends BaseTest {

    @DAO(JDBC)
    PostgresJdbcUsersDAO usersDataRepository;

    @AllureId("7")
    @Test
    void testAddingFriends(@Profile(username = "user", firstname = "test7") ProfileEntity user,
                           @Profile(username = "friend1", firstname = "test7", currency = "KZT") ProfileEntity friend1,
                           @Profile(username = "friend2", firstname = "test7", currency = "KZT") ProfileEntity friend2,
                           @Profile(username = "friend3", firstname = "test7", currency = "KZT") ProfileEntity friend3) {

        usersDataRepository.addUsers(user, friend1, friend2, friend3);
        user.setFriends(friend1, friend2, friend3);

        usersDataRepository.updateUser(user);
        Assertions.assertEquals(user.getFriends().toString(), of(friend1, friend2, friend3).toString());

        usersDataRepository.removeAll(user, friend1, friend2, friend3);
    }

}