package niffler.jupiter.di.dao;

import lombok.SneakyThrows;
import niffler.database.dao.PostgresHibernateUsersDAO;
import niffler.database.dao.PostgresJdbcUsersDAO;
import niffler.database.dao.PostgresSpringJdbcUsersDAO;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

public class DAOResolver implements TestInstancePostProcessor {
    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DAO.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> setImplementation(testInstance, field));
    }

    @SneakyThrows
    private void setImplementation(Object testInstance, Field field) {

        switch (field.getAnnotation(DAO.class).value()) {
            case SPRING -> field.set(testInstance, new PostgresSpringJdbcUsersDAO());
            case HIBERNATE -> field.set(testInstance, new PostgresHibernateUsersDAO());
            case JDBC -> field.set(testInstance, new PostgresJdbcUsersDAO());
        }

    }
}
