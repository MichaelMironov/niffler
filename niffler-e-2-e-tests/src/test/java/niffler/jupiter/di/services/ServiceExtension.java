package niffler.jupiter.di.services;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import niffler.database.dao.UserRepository;
import niffler.database.dto.UserCreateDto;
import niffler.database.interceptor.TransactionInterceptor;
import niffler.database.service.UserService;
import niffler.mapper.AuthoritiesReadMapper;
import niffler.mapper.UserCreateMapper;
import niffler.mapper.UserReadMapper;
import niffler.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Proxy;

import static niffler.jupiter.di.auth.AuthoriseExtension.NAMESPACE_AUTH_USERS;

public class ServiceExtension implements BeforeAllCallback, ParameterResolver, AfterTestExecutionCallback {

    private SessionFactory sessionFactory;
    private Session session;
    private UserService userService;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        sessionFactory = HibernateUtil.buildSessionFactory();
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserService.class) &&
                parameterContext.getParameter().isAnnotationPresent(WithUserService.class);
    }

    @SneakyThrows
    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);

        final UserRepository userRepository = new UserRepository(session);
        final AuthoritiesReadMapper authoritiesReadMapper = new AuthoritiesReadMapper();
        final UserReadMapper userReadMapper = new UserReadMapper(authoritiesReadMapper);
        final UserCreateMapper userCreateMapper = new UserCreateMapper();

        userService = new ByteBuddy().subclass(UserService.class).method(ElementMatchers.any()).intercept(MethodDelegation.to(transactionInterceptor))
                .make().load(UserService.class.getClassLoader()).getLoaded()
                .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                .newInstance(userRepository, userReadMapper, userCreateMapper);

        return userService;
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        final UserCreateDto userCreateDto = (UserCreateDto) context.getStore(NAMESPACE_AUTH_USERS).get("AuthoriseUsers");
        userService.delete(userCreateDto.authorities().getUser().getId());
    }
}
