package niffler.jupiter.di.auth;

import niffler.database.entity.auth.UserEntity;
import org.junit.jupiter.api.extension.*;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.List;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.extension.ExtensionContext.Namespace;

public class AuthoriseExtension implements ParameterResolver, AfterTestExecutionCallback, BeforeTestExecutionCallback {

    public static final Namespace NAMESPACE_AUTH_USERS = Namespace.create(niffler.jupiter.di.auth.UserEntity.class);

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        final List<niffler.jupiter.di.auth.UserEntity> usersEntity = stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(niffler.jupiter.di.auth.UserEntity.class))
                .map(parameter -> parameter.getAnnotation(niffler.jupiter.di.auth.UserEntity.class)).toList();
        usersEntity.forEach(userEntity -> createUser(userEntity, context));
    }

    private void createUser(niffler.jupiter.di.auth.UserEntity userEntity, ExtensionContext context) {

//        User user = new User();
//        user.setUsername(userEntity.username());
//        user.setPassword(userEntity.password());
//
//        Set<Authorities> authoritiesSet = stream(userEntity.authorities())
//                .map(authority -> Authorities.builder().user(user).authority(authority).build()).collect(Collectors.toSet());
//        user.setAuthorities(authoritiesSet);
//        String temp = userEntity.password();
//        context.getStore(NAMESPACE_AUTH_USERS).put("Auth", user);
//        saveEntity(userEntity, context, user);
//        user.setPassword(temp);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserEntity.class) &&
                parameterContext.getParameter().isAnnotationPresent(niffler.jupiter.di.auth.UserEntity.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE_AUTH_USERS).get("Auth");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {


    }

    private void saveEntity(niffler.jupiter.di.auth.UserEntity userEntity, ExtensionContext context, UserEntity user) {
        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(userEntity.password()));

    }
}
