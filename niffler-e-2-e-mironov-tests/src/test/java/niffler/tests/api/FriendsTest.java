package niffler.tests.api;

import io.qameta.allure.AllureId;
import niffler.api.friend.FriendsClient;
import niffler.data.json.FriendJson;
import niffler.data.json.UserJson;
import niffler.jupiter.auth.ApiLogin;
import niffler.tests.ui.BaseTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class FriendsTest extends BaseTest {

    @Test
    void test() throws IOException {

        FriendsClient friendsClient = new FriendsClient();

        final List<UserJson> spendtest41 = friendsClient.invitations("spendtest41");

        for (UserJson userJson : spendtest41) {

            System.out.println(userJson.getUserName());
        }

    }

    @Test
    void testFriends() throws IOException {

        FriendsClient friendsClient = new FriendsClient();

        final List<UserJson> spendtest41 = friendsClient.friends("dima", true);
        System.out.println(spendtest41);

    }

    @AllureId("12")
    @Test
    void testAdd() throws IOException {

        FriendsClient friendsClient = new FriendsClient();
        FriendJson friendJson = new FriendJson();
        friendJson.setUsername("ezra.kautzer");
        friendsClient.addFriend("mike", friendJson);

    }

    @AllureId("12")
    @Test
    void testRemove() throws IOException {

        FriendsClient friendsClient = new FriendsClient();
        FriendJson friendJson = new FriendJson();
        friendJson.setUsername("ezra.kautzer");
        final List<UserJson> mike = friendsClient.removeFriend("mike", "ezra.kautzer");
        System.out.println(mike);

    }
}
