package niffler.jupiter;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.extension.ExtensionContext;

public class BeforeSuiteExtension implements AroundAllTestsExtension {
    @Override
    public void beforeAllTests(ExtensionContext context) {
        System.out.println("BEFORE SUITE!");
        Configuration.browserSize = "1920x1080";
    }

    @Override
    public void afterAllTests() {
        System.out.println("AFTER SUITE!");
    }
}
