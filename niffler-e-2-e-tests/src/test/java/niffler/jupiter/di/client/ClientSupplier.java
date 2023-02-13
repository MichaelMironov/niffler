package niffler.jupiter.di.client;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ClientSupplier implements TestInstancePostProcessor {


    @Override
    public void postProcessTestInstance(final Object testInstance, final ExtensionContext context) throws Exception {
        Field[] fields = testInstance.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Client.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> createService(testInstance, field));
    }

    private void createService(final Object testInstance, final Field field) {
        try {
            field.set(testInstance, field.getType().getDeclaredConstructor().newInstance());
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
