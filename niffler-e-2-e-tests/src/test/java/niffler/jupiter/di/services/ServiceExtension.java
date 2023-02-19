package niffler.jupiter.di.services;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import niffler.database.dao.UserRepository;
import niffler.database.interceptor.TransactionInterceptor;
import niffler.database.service.UserService;
import niffler.mapper.AuthoritiesReadMapper;
import niffler.mapper.UserCreateMapper;
import niffler.mapper.UserReadMapper;
import niffler.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ServiceExtension implements TestInstancePostProcessor, BeforeAllCallback {

    private SessionFactory sessionFactory;
    private Session session;
    TransactionInterceptor transactionInterceptor;

    public static final ExtensionContext.Namespace NAMESPACE_SERVICES = ExtensionContext.Namespace.create(UserService.class);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

        sessionFactory = HibernateUtil.buildSessionFactory();
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        transactionInterceptor = new TransactionInterceptor(sessionFactory);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
        final Field[] declaredFields = testInstance.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).filter(field -> field.isAnnotationPresent(WithService.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> addService(testInstance, field, extensionContext));
    }

    @SneakyThrows
    private void addService(Object testInstance, Field field, ExtensionContext extensionContext) {

        //TODO: service types
//        final AnnotatedType annotatedType = field.getAnnotatedType();
//        if (annotatedType instanceof WithService) {
        final UserRepository userRepository = new UserRepository(session);
        final AuthoritiesReadMapper authoritiesReadMapper = new AuthoritiesReadMapper();
        final UserReadMapper userReadMapper = new UserReadMapper(authoritiesReadMapper);
        final UserCreateMapper userCreateMapper = new UserCreateMapper();

        UserService userService = new ByteBuddy().subclass(UserService.class)
                .method(ElementMatchers.any()).intercept(MethodDelegation.to(transactionInterceptor))
                .make().load(UserService.class.getClassLoader()).getLoaded()
                .getDeclaredConstructor(UserRepository.class, UserReadMapper.class, UserCreateMapper.class)
                .newInstance(userRepository, userReadMapper, userCreateMapper);
        field.set(testInstance, userService);
        extensionContext.getStore(NAMESPACE_SERVICES).put("Services", userService);
//        }
    }
}
