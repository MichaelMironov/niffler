package niffler.tests.db;

import niffler.database.dao.UsersDataRepository;
import niffler.database.entity.userdata.UsersEntity;
import niffler.jupiter.di.session.SessionExtension;
import niffler.jupiter.di.session.WithRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.util.List.of;

@ExtendWith(SessionExtension.class)
public class UserdataTest {

    @WithRepository
    UsersDataRepository usersDataRepository;

    UsersEntity user = UsersEntity.builder().username("user").currency("RUB").firstname("user").build();
    UsersEntity friend1 = UsersEntity.builder().username("friend1").currency("KZT").firstname("test1").build();
    UsersEntity friend2 = UsersEntity.builder().username("friend2").currency("RUB").firstname("test2").build();
    UsersEntity friend3 = UsersEntity.builder().username("friend3").currency("KZT").firstname("test3").build();

    @BeforeEach
    void setFriends() {
        //Add test users in database
        usersDataRepository.addAll(user, friend1, friend2, friend3);
        user.setFriends(friend1, friend2, friend3);
    }

    @Test
    void testAddingFriends() {
        usersDataRepository.updateUser(user);
        Assertions.assertEquals(user.getFriends().toString(), of(friend1, friend2, friend3).toString());
    }

    @AfterEach
    void deletingTestUsers() {
        usersDataRepository.delete(user, friend1, friend2, friend3);
    }
}