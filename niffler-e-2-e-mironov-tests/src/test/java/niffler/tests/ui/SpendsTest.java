package niffler.tests.ui;

import niffler.data.entity.Spend;
import niffler.jupiter.di.spend.SpendInjector;
import niffler.jupiter.di.spend.WithSpend;
import niffler.data.enums.CurrencyValues;
import niffler.tests.ui.steps.CheckSteps;
import niffler.tests.ui.steps.LoginSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SpendInjector.class)
class SpendsTest extends BaseTest {

    @WithSpend(name ="mike", amount = 5000.0, currency = CurrencyValues.RUB, category = "Бары", description = "Тест добавления затрат")
    Spend spend;

    @Test
    void test() {

        CheckSteps.findAllSpends();
        LoginSteps.login();

        new CheckSteps().checkSpends(spend);

    }
}
