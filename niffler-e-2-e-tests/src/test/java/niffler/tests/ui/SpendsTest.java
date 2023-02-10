package niffler.tests.ui;

import niffler.database.entity.Currency;
import niffler.database.entity.Spend;
import niffler.jupiter.di.SpendInjector;
import niffler.jupiter.di.WithSpend;
import niffler.tests.ui.steps.CheckSteps;
import niffler.tests.ui.steps.LoginSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpendInjector.class)
class SpendsTest extends BaseTest {

    @WithSpend(amount = 5000.0, currency = Currency.RUB, category = "Бары", description = "Тест добавления затрат")
    Spend spend;

    @Test
    void test() {

        CheckSteps.findAllSpends();
        LoginSteps.login();

        new CheckSteps().checkSpends(spend);

    }
}
