package niffler.tests.db;

import niffler.database.dao.UsersDataRepository;
import niffler.database.entity.userdata.Users;
import niffler.jupiter.di.session.SessionExtension;
import niffler.jupiter.di.session.WithRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.util.List.*;

@ExtendWith(SessionExtension.class)
public class UserdataTest {

    @WithRepository
    UsersDataRepository usersDataRepository;

    Users user = Users.builder().username("user").currency("RUB").firstname("user").build();
    Users friend1 = Users.builder().username("friend1").currency("KZT").firstname("test1").build();
    Users friend2 = Users.builder().username("friend2").currency("RUB").firstname("test2").build();
    Users friend3 = Users.builder().username("friend3").currency("KZT").firstname("test3").build();

    @BeforeEach
    void addTestUsersInDatabase(){
        usersDataRepository.addAll(user, friend1, friend2, friend3);
    }

    @Test
    void test() {
        usersDataRepository.addFriendsToUser(of(friend1, friend2, friend3), user);
    }

    @AfterEach
    void deletingTestUsers(){
        usersDataRepository.delete(user, friend1, friend2, friend3);
    }
}