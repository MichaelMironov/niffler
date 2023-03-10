package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.data.json.FriendJson;
import niffler.data.json.UserJson;
import niffler.jupiter.auth.ApiLogin;
import niffler.jupiter.auth.GenerateUser;
import niffler.jupiter.di.user.User;
import org.junit.jupiter.api.Test;

import static niffler.jupiter.auth.CreateUserExtension.Selector.NESTED;

class FriendsTests extends BaseTest {

    @AllureId("14")
    @Test
    @ApiLogin(nifflerUser = @GenerateUser(username = "aa1", password = "12345"))
    void addFriend(@User(selector = NESTED) UserJson user, FriendJson friendJson){
        Selenide.open()
    }
}
