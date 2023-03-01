package niffler.jupiter.di.auth;

import io.qameta.allure.AllureId;
import niffler.database.DataBase;
import niffler.database.dao.PostgresHibernateUsersDAO;
import niffler.database.entity.auth.UserEntity;
import niffler.database.jpa.EmfContext;
import org.junit.jupiter.api.extension.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class AuthoriseExtension implements ParameterResolver, AfterTestExecutionCallback, BeforeTestExecutionCallback {

    public static final Namespace NAMESPACE_AUTH_USERS = Namespace.create(AuthUser.class);
    private final PostgresHibernateUsersDAO hibernateUsersDAO = new PostgresHibernateUsersDAO();

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {

        final List<AuthUser> usersEntity = stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(AuthUser.class))
                .map(parameter -> parameter.getAnnotation(AuthUser.class)).toList();

        List<UserEntity> userEntities = new ArrayList<>();
        for (AuthUser authUser : usersEntity) {
            userEntities.add(createUser(authUser));
        }
        context.getStore(NAMESPACE_AUTH_USERS).put(getId(context), userEntities);
    }

    private UserEntity createUser(AuthUser authUser) {

        return UserEntity.builder()
                .username(authUser.username())
                .password(authUser.password())
                .credentialsNonExpired(authUser.credentialsNonExpired())
                .accountNonLocked(authUser.accountNonLocked())
                .accountNonExpired(authUser.accountNonExpired())
                .enabled(authUser.enabled())
                .build();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserEntity.class) &&
                parameterContext.getParameter().isAnnotationPresent(AuthUser.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        AuthUser annotation = parameterContext.getParameter().getAnnotation(AuthUser.class);
        @SuppressWarnings("unchecked") List<UserEntity> entities =
                (List<UserEntity>) extensionContext.getStore(NAMESPACE_AUTH_USERS).get(getId(extensionContext));
        return entities.stream()
                .filter(userEntity -> userEntity.getUsername().equals(annotation.username()))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        @SuppressWarnings("unchecked") List<UserEntity> entities =
                (List<UserEntity>) extensionContext.getStore(NAMESPACE_AUTH_USERS).get(getId(extensionContext));

        final List<UserEntity> userEntities = entities.stream().map(userEntity -> hibernateUsersDAO.getByUsername(userEntity.getUsername())).toList();
        for (UserEntity userEntity : userEntities) {
            hibernateUsersDAO.deleteUser(userEntity);
        }
    }

    /**
     * @param context by current test
     * @return string value from allure_id annotation
     */
    private String getId(final ExtensionContext context) {
        return context.getRequiredTestMethod().getAnnotation(AllureId.class).value();
    }
}
