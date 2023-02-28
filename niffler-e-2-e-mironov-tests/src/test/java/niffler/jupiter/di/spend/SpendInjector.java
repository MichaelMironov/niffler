package niffler.jupiter.di.spend;

import lombok.SneakyThrows;
import niffler.database.entity.spends.Spend;
import niffler.database.repostiory.CategoriesRepository;
import niffler.database.repostiory.SpendsRepository;
import niffler.database.entity.categories.Categories;
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

        final WithSpend annotation = field.getAnnotation(WithSpend.class);

        Spend spend = new Spend.Builder()
                .setUsername(annotation.name())
                .setCurrency(annotation.currency())
                .setAmount(annotation.amount())
                .setDescription(annotation.description())
                .setCategory(getUUIDbyCategory(annotation.category()))
                .build();

        try {
            final Spend created = SpendsRepository.getInstance().save(spend);
            created.setCategoryName(annotation.category());
            field.set(testInstance, created);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать тестовые данные");
        }
    }

    private UUID getUUIDbyCategory(String categoryName) {
        final Optional<Categories> expectedCategory = CategoriesRepository.getInstance().findAll()
                .stream().filter(category -> category.getCategory().equals(categoryName)).findFirst();
        return expectedCategory.isPresent() ? expectedCategory.get().getId() : Assertions.fail("Не найден UUID по указанному описанию");
    }
}
