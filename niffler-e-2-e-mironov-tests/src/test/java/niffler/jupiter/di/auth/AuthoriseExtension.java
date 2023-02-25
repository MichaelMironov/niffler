package niffler.jupiter.di.auth;

import niffler.database.entity.authorities.Authorities;
import niffler.database.entity.auth.User;
import org.hibernate.Session;
import org.junit.jupiter.api.extension.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static niffler.jupiter.di.session.SessionExtension.NAMESPACE_SESSION;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class AuthoriseExtension implements ParameterResolver, AfterTestExecutionCallback, BeforeTestExecutionCallback {

    public static final Namespace NAMESPACE_AUTH_USERS = Namespace.create(UserEntity.class);

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        final List<UserEntity> usersEntity = stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(UserEntity.class))
                .map(parameter -> parameter.getAnnotation(UserEntity.class)).toList();
        usersEntity.forEach(userEntity -> createUser(userEntity, context));
    }

    private void createUser(UserEntity userEntity, ExtensionContext context) {

        User user = new User();
        user.setUsername(userEntity.username());
        user.setPassword(userEntity.password());

        Set<Authorities> authoritiesSet = stream(userEntity.authorities())
                .map(authority -> Authorities.builder().user(user).authority(authority).build()).collect(Collectors.toSet());
        user.setAuthorities(authoritiesSet);
        String temp = userEntity.password();
        context.getStore(NAMESPACE_AUTH_USERS).put("Auth", user);
        saveEntity(userEntity, context, user);
        user.setPassword(temp);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(User.class) &&
                parameterContext.getParameter().isAnnotationPresent(UserEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE_AUTH_USERS).get("Auth");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        final Session session = (Session) context.getStore(NAMESPACE_SESSION).get("Session");
        session.remove(context.getStore(NAMESPACE_AUTH_USERS).get("Auth"));
        session.flush();
    }

    private void saveEntity(UserEntity userEntity, ExtensionContext context, User user) {
        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userEntity.password()));
        final Session session = (Session) context.getStore(NAMESPACE_SESSION).get("Session");
        session.persist(user);
        session.flush();
    }
}
