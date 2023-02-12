package niffler.jupiter.di.spend;

import lombok.SneakyThrows;
import niffler.database.dao.CategoriesDao;
import niffler.database.dao.SpendsDao;
import niffler.database.entity.Categories;
import niffler.database.entity.Currency;
import niffler.database.entity.Spend;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class SpendInjector implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Field[] fields = testInstance.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(WithSpend.class) && field.getType().isAssignableFrom(Spend.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> createInstance(testInstance, field));
    }

    @SneakyThrows
    private void createInstance(Object testInstance, Field field) {

        final double amount = field.getAnnotation(WithSpend.class).amount();
        final Currency currency = field.getAnnotation(WithSpend.class).currency();
        final String category = field.getAnnotation(WithSpend.class).category();
        final String description = field.getAnnotation(WithSpend.class).description();

        Spend spend = new Spend.Builder()
                .setUsername("mike")
                .setCurrency(currency)
                .setAmount(amount)
                .setDescription(description)
                .setCategoryId(getUUIDbyDescription(category))
                .build();

        try {
            final Spend created = SpendsDao.getInstance().create(spend);
            created.setCategoryName(category);
            field.set(testInstance, created);
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
