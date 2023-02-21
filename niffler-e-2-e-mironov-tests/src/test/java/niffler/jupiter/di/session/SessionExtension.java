package niffler.jupiter.di.session;

import lombok.SneakyThrows;
import niffler.database.dao.UserRepository;
import niffler.database.interceptor.TransactionInterceptor;
import niffler.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;

public class SessionExtension implements TestInstancePostProcessor, BeforeAllCallback, AfterAllCallback {

    private SessionFactory sessionFactory;
    private Session session;
    TransactionInterceptor transactionInterceptor;

    public static final ExtensionContext.Namespace NAMESPACE_SESSION = ExtensionContext.Namespace.create(Session.class);

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {

        sessionFactory = HibernateUtil.buildSessionFactory();
        session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
        transactionInterceptor = new TransactionInterceptor(sessionFactory);
        session.beginTransaction();
        session.doWork(connection -> connection.setAutoCommit(true));
        extensionContext.getStore(NAMESPACE_SESSION).put("Session", session);
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext extensionContext) throws Exception {
        final Field[] declaredFields = testInstance.getClass().getDeclaredFields();
        Arrays.stream(declaredFields).filter(field -> field.isAnnotationPresent(WithRepository.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> addSession(testInstance, field, extensionContext));
    }

    @SneakyThrows
    private void addSession(Object testInstance, Field field, ExtensionContext extensionContext) {
        UserRepository userRepository = new UserRepository(session);
        field.set(testInstance, userRepository);
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Objects.requireNonNull(sessionFactory).close();
    }
}
