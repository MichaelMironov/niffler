package niffler.jupiter;

import com.codeborne.selenide.Configuration;
import niffler.database.dao.SpendsDao;
import niffler.tests.ui.steps.CheckSteps;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BeforeSuiteExtension implements AroundAllTestsExtension {
    @Override
    public void beforeAllTests(ExtensionContext context) {
        System.out.println("BEFORE SUITE!");
        Configuration.browserSize = "1920x1080";
    }

    @Override
    public void afterAllTests() {
        Assertions.assertTrue(SpendsDao.getInstance().clear());
        CheckSteps.findAllSpends();
        System.out.println("AFTER SUITE!");
    }
}
