package niffler.jupiter.di.user;

import io.qameta.allure.AllureId;
import lombok.SneakyThrows;
import niffler.data.model.UserModel;
import niffler.jupiter.di.user.User.UserType;
import org.junit.jupiter.api.extension.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static niffler.jupiter.di.user.User.UserType.ADMIN;

public class UsersSupplier implements ParameterResolver, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Queue<UserModel> ADMIN_USERS = new ConcurrentLinkedQueue<>();
    private static final Queue<UserModel> COMMON_USERS = new ConcurrentLinkedQueue<>();
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersSupplier.class);

    static {
        ADMIN_USERS.add(new UserModel("dima", "12345"));
        ADMIN_USERS.add(new UserModel("mike", "mir"));
        COMMON_USERS.add(new UserModel("test", "12345"));
        COMMON_USERS.add(new UserModel("test", "12345"));
    }

    /**
     * Getting user from USERS_QUEUE with waiting in while cycle until he wasn't null
     */
    @Override
    public void beforeTestExecution(final ExtensionContext context) throws Exception {

        String id = getId(context);

        List<UserType> userTypes = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(p -> p.isAnnotationPresent(User.class))
                .map(p -> p.getAnnotation(User.class))
                .map(User::value).toList();

        Map<UserModel, UserType> users = new HashMap<>();

        for (UserType type : userTypes) {
            UserModel user = null;
            while (user == null) {
                user = type == ADMIN ? ADMIN_USERS.poll() : COMMON_USERS.poll();
            }
            users.put(user, type);
        }
        context.getStore(NAMESPACE).put(id, users);
        System.out.println("Test_" + id + "\t" + Thread.currentThread().getName() + ". STORAGE: BEFORE - " + context.getStore(NAMESPACE).get(id));
    }

    @Override
    public void afterTestExecution(final ExtensionContext context) throws Exception {

        getUsersStorage(context).forEach((user, type) -> {
            if (type == ADMIN) ADMIN_USERS.add(user);
            else COMMON_USERS.add(user);
        });

        System.out.println("Test_" + getId(context) + "\t" + Thread.currentThread().getName() +
                ". STORAGE: AFTER - " + context.getStore(NAMESPACE).get(getId(context)));
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @SneakyThrows
    @Override
    public UserModel resolveParameter(final ParameterContext parameterContext, final ExtensionContext context) throws ParameterResolutionException {
        return getUsersStorage(context).entrySet().stream()
                .filter(entry -> parameterContext.getParameter().getAnnotation(User.class).value().equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().orElseThrow();
    }

    /**
     * @param context by current test
     * @return string value from allure_id annotation
     */
    private String getId(final ExtensionContext context) {
        return context.getRequiredTestMethod().getAnnotation(AllureId.class).value();
    }

    @SuppressWarnings("unchecked")
    private Map<UserModel, UserType> getUsersStorage(final ExtensionContext context) {
        return context.getStore(NAMESPACE).get(getId(context), Map.class);
    }
}
