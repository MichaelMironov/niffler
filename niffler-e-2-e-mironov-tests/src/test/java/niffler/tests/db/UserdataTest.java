package niffler.tests.db;

import niffler.database.repostiory.UsersDataRepository;
import niffler.database.entity.userdata.ProfileEntity;

import org.junit.jupiter.api.*;

import static java.util.List.of;

@Disabled
public class UserdataTest {

    //TODO: create genereated user in paramter resolver
    UsersDataRepository usersDataRepository;

    ProfileEntity user = ProfileEntity.builder().username("user").currency("RUB").firstname("user").build();
    ProfileEntity friend1 = ProfileEntity.builder().username("friend1").currency("KZT").firstname("test1").build();
    ProfileEntity friend2 = ProfileEntity.builder().username("friend2").currency("RUB").firstname("test2").build();
    ProfileEntity friend3 = ProfileEntity.builder().username("friend3").currency("KZT").firstname("test3").build();

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