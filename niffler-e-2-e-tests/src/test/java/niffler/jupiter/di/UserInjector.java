package niffler.jupiter.di;

import lombok.SneakyThrows;
import niffler.database.dao.CategoriesDao;
import niffler.database.dao.SpendsDao;
import niffler.database.entity.Categories;
import niffler.database.entity.Spends;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class UserInjector implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Field[] fields = testInstance.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(Inject.class) && field.getType().isAssignableFrom(Spends.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> createInstance(testInstance, field));
    }

    @SneakyThrows
    private void createInstance(Object testInstance, Field field) {

        final double amount = field.getAnnotation(Inject.class).amount();
        final String currency = field.getAnnotation(Inject.class).currency();
        final String category = field.getAnnotation(Inject.class).category();
        final String description = field.getAnnotation(Inject.class).description();

        Spends spends = new Spends(null, "mike", null, currency, amount, description, getUUIDbyDescription(category));
        try {
            field.set(testInstance, SpendsDao.getInstance().create(spends));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать тестовые данные");
        }
    }

    private UUID getUUIDbyDescription(String description) {
        Categories categories = new Categories();
        categories.setDescription(description);
        final Optional<Categories> expectedCategory = CategoriesDao.getInstance().findAll()
                .stream().filter(category -> category.getDescription().equals(categories.getDescription()))
                .findFirst();
        return expectedCategory.isPresent() ? expectedCategory.get().getId() : Assertions.fail("Не найден UUID по указанному описанию");
    }
}
