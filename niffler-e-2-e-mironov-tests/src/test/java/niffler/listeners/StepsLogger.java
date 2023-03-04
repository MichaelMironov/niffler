package niffler.listeners;

import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;

import java.util.concurrent.atomic.AtomicInteger;

public class StepsLogger implements StepLifecycleListener {
    final AtomicInteger stepCount = new AtomicInteger();

    @Override
    public void beforeStepStop(StepResult result) {

        System.out.println(result.getName());

//        getLifecycle().updateStep(stepResult -> result.setName(stepCount.incrementAndGet() + result.getName()));
    }
}
