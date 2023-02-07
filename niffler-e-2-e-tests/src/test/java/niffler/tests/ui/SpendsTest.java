package niffler.tests.ui;

import niffler.database.dao.SpendsDao;
import niffler.database.entity.Spends;
import niffler.jupiter.di.Inject;
import niffler.jupiter.di.Injector;
import niffler.tests.ui.steps.CheckSteps;
import niffler.tests.ui.steps.LoginSteps;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Injector
class SpendsTest extends BaseTest {

    @Inject(amount = 5000.0, currency = "RUB", category = "Бары", description = "Тест добавления затрат")
    Spends spends;

    @Test
    void test() {

        CheckSteps.findAllSpends();
        LoginSteps.login();
        CheckSteps.checkSpends(spends.getSpendDate(), "5000", "RUB", "Бары", "Тест добавления затрат");
    }

    @AfterEach
    void after() {
        Assertions.assertTrue(SpendsDao.getInstance().clear());
        CheckSteps.findAllSpends();
    }
}
