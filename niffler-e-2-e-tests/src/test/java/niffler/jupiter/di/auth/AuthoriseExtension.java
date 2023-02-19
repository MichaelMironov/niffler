package niffler.jupiter.di.auth;

import niffler.database.dto.UserCreateDto;
import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.user.AccountStatus;
import niffler.database.entity.user.Credentials;
import niffler.database.service.UserService;
import org.junit.jupiter.api.extension.*;

import java.util.*;

import static niffler.jupiter.di.services.ServiceExtension.NAMESPACE_SERVICES;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class AuthoriseExtension implements ParameterResolver, AfterTestExecutionCallback, BeforeTestExecutionCallback {

    public static final Namespace NAMESPACE_AUTH_USERS = Namespace.create(AuthUser.class);

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        final List<AuthUser> authUsers = Arrays.stream(context.getRequiredTestMethod().getParameters()).filter(parameter -> parameter.isAnnotationPresent(AuthUser.class))
                .map(parameter -> parameter.getAnnotation(AuthUser.class)).toList();
        authUsers.forEach(authUser -> createDto(authUser, context));
    }

    private void createDto(AuthUser authUser, ExtensionContext context) {
        final UserCreateDto userCreateDto = new UserCreateDto(
                Credentials.builder().username(authUser.username()).password(authUser.password()).build(),
                AccountStatus.builder().enabled(true).accountNonExpired(true).accountNonLocked(true).credentialsNonExpired(true).build(),
                Authorities.builder().authority(authUser.authority()).build());
        Set<UserCreateDto> users = new HashSet<>();
        users.add(userCreateDto);
        context.getStore(NAMESPACE_AUTH_USERS).put("AuthoriseUsers", users);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserCreateDto.class) &&
                parameterContext.getParameter().isAnnotationPresent(AuthUser.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final HashSet<UserCreateDto> authoriseUsers = (HashSet<UserCreateDto>) extensionContext.getStore(NAMESPACE_AUTH_USERS).get("AuthoriseUsers");

        return authoriseUsers.stream()
                .filter(authoriseUser -> parameterContext.getParameter()
                        .getAnnotation(AuthUser.class).username().equals(authoriseUser.credentials().getUsername()))
                .findFirst().orElseThrow(() -> new NoSuchElementException("UserDto not found"));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        final HashSet<UserCreateDto> authoriseUsers = (HashSet<UserCreateDto>) context.getStore(NAMESPACE_AUTH_USERS).get("AuthoriseUsers");
        final UserService service = (UserService) context.getStore(NAMESPACE_SERVICES).get("Services");
        authoriseUsers.forEach(authoriseUser -> service.delete(authoriseUser.authorities().getUser().getId()));
    }
}
