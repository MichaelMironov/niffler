package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.jupiter.auth.ApiLogin;
import niffler.jupiter.auth.GenerateUser;
import niffler.pages.ProfilePage;
import org.junit.jupiter.api.Test;


class ProfileTest extends BaseTest {

    @Test
    @AllureId("10")
    @ApiLogin(nifflerUser = @GenerateUser)
    void avatarMustMatchAfterSetInProfile() {
        Selenide.open(ProfilePage.URL, ProfilePage.class)
                .waitForPageLoaded()
                .setPhoto("data/img/profile_photo.png")
                .submitProfile()
                .toMainPage()
                .getHeader()
                .checkProfilePhoto("data/img/profile_photo.png");
    }
}
