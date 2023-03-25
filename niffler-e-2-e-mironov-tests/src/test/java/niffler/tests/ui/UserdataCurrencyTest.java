package niffler.tests.ui;

import io.qameta.allure.AllureId;
import niffler.data.enums.CurrencyValues;
import niffler.data.json.UserJson;
import niffler.database.dao.UsersDAO;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.jupiter.auth.ApiLogin;
import niffler.jupiter.di.dao.DAO;
import niffler.jupiter.di.dao.DAOResolver;
import niffler.jupiter.di.user.User;
import niffler.pages.ProfilePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
import static niffler.jupiter.auth.CreateUserExtension.Selector.NESTED;
import static niffler.jupiter.di.dao.DAOType.SPRING;

@ExtendWith(DAOResolver.class)
class UserdataCurrencyTest {

    @DAO(SPRING)
    private UsersDAO usersDAO;
    private String originalCurrency;

    @BeforeEach
    void createTestData() {
        ProfileEntity user = usersDAO.getByUsername("mike");
        originalCurrency = user.getCurrency();
        String testedCurrency = "KZT";
        user.setCurrency(testedCurrency);
        usersDAO.updateUser(user);
    }

    @AllureId("18")
    @Test
    @ApiLogin(username = "mike", password = "mir")
    void checkUpdatedCurrency() {
        open(ProfilePage.URL, ProfilePage.class)
                .checkCurrency(CurrencyValues.KZT);
    }

    @AfterEach
    void rollbackChangedCurrency() {
        ProfileEntity user = usersDAO.getByUsername("mike");
        user.setCurrency(originalCurrency);
        usersDAO.updateUser(user);
    }

}
