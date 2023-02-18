package niffler.jupiter.di.auth;

import lombok.SneakyThrows;
import niffler.database.dto.UserCreateDto;
import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.user.AccountStatus;
import niffler.database.entity.user.Credentials;
import niffler.database.service.UserService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class AuthoriseExtension implements TestInstancePostProcessor {

    public static final Namespace NAMESPACE_AUTH_USERS = Namespace.create(UserService.class);

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Field[] fields = testInstance.getClass().getDeclaredFields();
        Arrays.stream(fields).filter(field -> field.isAnnotationPresent(AuthUser.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> createUser(testInstance, field, context));
    }

    @SneakyThrows
    private void createUser(Object testInstance, Field field, ExtensionContext context) {
        final UserCreateDto userCreateDto = new UserCreateDto(
                Credentials.builder().username(field.getAnnotation(AuthUser.class).username()).password(field.getAnnotation(AuthUser.class).password()).build(),
                AccountStatus.builder().enabled(true).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).build(),
                Authorities.builder().authority(field.getAnnotation(AuthUser.class).authority()).build());
        field.set(testInstance, userCreateDto);
        context.getStore(NAMESPACE_AUTH_USERS).put("AuthoriseUsers", userCreateDto);
    }

}
