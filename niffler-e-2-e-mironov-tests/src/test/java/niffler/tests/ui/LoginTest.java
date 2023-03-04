package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.data.json.UserJson;
import niffler.jupiter.auth.GenerateUser;
import niffler.jupiter.di.user.User;
import niffler.pages.WelcomePage;
import niffler.tests.ui.steps.StepEnumerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static niffler.jupiter.auth.CreateUserExtension.Selector.METHOD;

@ExtendWith(StepEnumerator.class)
public class LoginTest extends BaseTest {

    @Test
    @AllureId("9")
    @GenerateUser()
    void mainPageShouldBeDisplayedAfterSuccessLogin(@User(selector = METHOD) UserJson user) {
        Selenide.open(WelcomePage.URL, WelcomePage.class)
                .doLogin()
                .fillLoginPage(user.getUserName(), user.getPassword())
                .submit()
                .waitForPageLoaded();
    }
}
