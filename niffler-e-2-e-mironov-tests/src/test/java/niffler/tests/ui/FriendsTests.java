package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.data.json.UserJson;
import niffler.jupiter.auth.ApiLogin;
import niffler.jupiter.auth.GenerateUser;
import niffler.jupiter.di.friend.Friend;
import niffler.jupiter.di.friend.FriendsExtension;
import niffler.jupiter.di.user.User;
import niffler.pages.FriendsPage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.jupiter.auth.CreateUserExtension.Selector.METHOD;
@ExtendWith(FriendsExtension.class)
class FriendsTests extends BaseTest {

    @AllureId("16")
    @Test
    @GenerateUser
    @ApiLogin(username = "mike", password = "mir")
    void testAdd(@User(selector = METHOD, friend = true) UserJson friend) {

        Selenide.open(FriendsPage.URL, FriendsPage.class)
                .checkFriend(friend.getUserName());
    }

    @Disabled
    @AllureId("17")
    @Test
    void testAddTwoUsers(@Friend UserJson user, @Friend UserJson friend) {

        Selenide.open(FriendsPage.URL, FriendsPage.class)
                .checkFriend(friend.getUserName());
    }
}
