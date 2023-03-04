package niffler.tests.ui.steps;

import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.List;

public class StepEnumerator implements AfterEachCallback {
    int count = 0;

    @Override
    public void afterEach(final ExtensionContext context) throws Exception {
        Allure.getLifecycle().updateTestCase(testResult -> {
            final List<StepResult> steps = testResult.getSteps();
            for (StepResult step : steps) {
                step.setName(++count + ": " + step.getName());
            }
        });
    }
}
