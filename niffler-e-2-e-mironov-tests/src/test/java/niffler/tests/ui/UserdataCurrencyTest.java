package niffler.tests.ui;

import com.codeborne.selenide.Condition;
import niffler.database.dao.UsersDAO;
import niffler.database.entity.userdata.ProfileEntity;
import niffler.jupiter.di.dao.DAO;
import niffler.jupiter.di.dao.DAOResolver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
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

    @Test
    void loginTest() {
        step("Check login", () -> {
            open("http://127.0.0.1:3000/");
            $("a[href*='redirect']").click();
            $("input[name='username']").setValue("mike");
            $("input[name='password']").setValue("mir");
            $("button[type='submit']").click();
            $(".header__title").shouldBe(Condition.visible)
                    .shouldHave(Condition.text("Niffler. The coin keeper."));
        });
    }

    @AfterEach
    void rollbackChangedCurrency() {
        ProfileEntity user = usersDAO.getByUsername("mike");
        user.setCurrency(originalCurrency);
        usersDAO.updateUser(user);
    }

}
