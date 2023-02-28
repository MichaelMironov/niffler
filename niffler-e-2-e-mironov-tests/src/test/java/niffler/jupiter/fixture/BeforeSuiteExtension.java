package niffler.jupiter.fixture;

import com.codeborne.selenide.Configuration;
import niffler.database.repostiory.SpendsRepository;
import niffler.tests.ui.steps.CheckSteps;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BeforeSuiteExtension implements AroundAllTestsExtension {
    @Override
    public void beforeAllTests(ExtensionContext context) {
        System.out.println("BEFORE SUITE!");
        Configuration.browserSize = "1920x1080";
    }

    @Override
    public void afterAllTests() {
        SpendsRepository.getInstance().clear();
        CheckSteps.findAllSpends();
        System.out.println("AFTER SUITE!");
    }
}
