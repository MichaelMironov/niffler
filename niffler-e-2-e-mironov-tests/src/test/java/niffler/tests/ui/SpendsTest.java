package niffler.tests.ui;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.AllureId;
import niffler.data.enums.CurrencyValues;
import niffler.data.json.SpendJson;
import niffler.data.json.UserJson;
import niffler.database.entity.spends.Spend;
import niffler.jupiter.auth.ApiLogin;
import niffler.jupiter.auth.GenerateCategory;
import niffler.jupiter.auth.GenerateSpend;
import niffler.jupiter.auth.GenerateUser;
import niffler.jupiter.di.user.User;
import niffler.pages.MainPage;
import niffler.tests.ui.steps.CheckSteps;
import niffler.tests.ui.steps.LoginSteps;
import niffler.utils.DateUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static niffler.data.enums.CurrencyValues.KZT;
import static niffler.data.enums.CurrencyValues.RUB;
import static niffler.jupiter.auth.CreateUserExtension.Selector.NESTED;

class SpendsTest extends BaseTest {

    @Test
    @AllureId("11")
    @ApiLogin(nifflerUser = @GenerateUser(
            categories = {
                    @GenerateCategory("Бар"),
                    @GenerateCategory("Продукты")},
            spends = {
                    @GenerateSpend(amount = 700D, currency = KZT, category = "Бар", description = "Бар"),
                    @GenerateSpend(amount = 2000D, currency = RUB, category = "Продукты", date = "24 Feb 23", description = "Продукты")}))
    void userSpendsTest(@User(selector = NESTED) UserJson user) {

        SpendJson[] spends = user.getSpendJsons().toArray(new SpendJson[0]);

        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .checkTableContains(spends);
    }

    @AllureId("8")
    @Test
    @ApiLogin(username = "mike", password = "mir")
    void checkLastWeekSpendingTest() throws Exception {
        SpendJson expected = new SpendJson();
        expected.setSpendDate(DateUtils.fromString("02 Mar 23"));
        expected.setAmount(5000D);
        expected.setCurrency(CurrencyValues.RUB);
        expected.setCategory("Бары");
        expected.setDescription("Бары");

        Selenide.open(MainPage.URL, MainPage.class)
                .getSpendingTable()
                .clickByButton("Last week")
                .checkTableContains(expected);
    }

    //    @WithSpend(name ="mike", amount = 5000.0, currency = CurrencyValues.RUB, category = "Бары", description = "Тест добавления затрат")
    Spend spend;

    @AllureId("9")
    @Test
    @Disabled
    void test() {

        CheckSteps.findAllSpends();
        LoginSteps.login();

        new CheckSteps().checkSpends(spend);

    }
}
