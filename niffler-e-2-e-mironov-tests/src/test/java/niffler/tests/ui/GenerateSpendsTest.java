package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.data.json.SpendJson;
import niffler.data.json.UserJson;
import niffler.jupiter.auth.ApiLogin;
import niffler.jupiter.auth.GenerateCategory;
import niffler.jupiter.auth.GenerateSpend;
import niffler.jupiter.auth.GenerateUser;
import niffler.jupiter.di.user.User;
import niffler.pages.MainPage;
import org.junit.jupiter.api.Test;

import static niffler.data.enums.CurrencyValues.KZT;
import static niffler.data.enums.CurrencyValues.RUB;
import static niffler.jupiter.auth.CreateUserExtension.Selector.NESTED;

public class GenerateSpendsTest extends BaseTest {

    @Test
    @AllureId("11")
    @ApiLogin(nifflerUser = @GenerateUser(
            categories = {
                    @GenerateCategory("Бар"),
                    @GenerateCategory("Продукты")},
            spends = {
                    @GenerateSpend(amount = 700D, currency = KZT, category = "Бар", description = "test"),
                    @GenerateSpend(amount = 2000D, currency = RUB, category = "Продукты", date = "24 Feb 23", description = "test")}))
    void generatedSpendsTest(@User(selector = NESTED) UserJson user) {

        SpendJson[] spendJsons = user.getSpendJsons().toArray(new SpendJson[0]);
        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .checkTableContains(spendJsons);
    }
}
