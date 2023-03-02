package niffler.jupiter.fixture;

import com.codeborne.selenide.Configuration;
import niffler.database.repostiory.SpendsRepository;
import niffler.tests.ui.steps.CheckSteps;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface AroundAllTestsExtension extends BeforeAllCallback {

    default void beforeAllTests(ExtensionContext context) {
        Configuration.browserSize = "1920x1080";
    }

    default void afterAllTests() {
        SpendsRepository.getInstance().clear();
        CheckSteps.findAllSpends();
    }

    @Override
    default void beforeAll(ExtensionContext context) throws Exception {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).
                getOrComputeIfAbsent(this.getClass(),
                        k -> {
                            beforeAllTests(context);
                            return (ExtensionContext.Store.CloseableResource) this::afterAllTests;
                        }
                );
    }
}
