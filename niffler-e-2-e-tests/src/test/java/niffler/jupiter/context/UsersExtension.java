package niffler.jupiter.context;

import io.qameta.allure.AllureId;
import niffler.jupiter.context.User.UserType;
import niffler.model.UserModel;
import org.junit.jupiter.api.extension.*;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static niffler.jupiter.context.User.UserType.ADMIN;
import static niffler.jupiter.context.User.UserType.COMMON;

public class UsersExtension implements ParameterResolver, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Queue<UserModel> ADMIN_USERS = new ConcurrentLinkedQueue<>();
    private static final Queue<UserModel> COMMON_USERS = new ConcurrentLinkedQueue<>();
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersExtension.class);

    static {
        ADMIN_USERS.add(new UserModel("dima", "12345"));
        ADMIN_USERS.add(new UserModel("mike", "mir"));
        COMMON_USERS.add(new UserModel("test", "12345"));
    }

    /**
     * Getting user from USERS_QUEUE with waiting in while cycle until he wasn't null
     */
    @Override
    public void beforeTestExecution(final ExtensionContext context) throws Exception {
        String id = getId(context);
        UserModel user = null;
        final UserType desiredUserType = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(p -> p.isAnnotationPresent(User.class))
                .map(p -> p.getAnnotation(User.class))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Required user not found in current context"))
                .value();

        while (user == null) {
            user = desiredUserType == ADMIN ? ADMIN_USERS.poll() : COMMON_USERS.poll();
        }
        context.getStore(NAMESPACE).put(id, Map.of(desiredUserType, user));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterTestExecution(final ExtensionContext context) throws Exception {
        String id = getId(context);
        final Map<UserType, UserModel> userModel = context.getStore(NAMESPACE).get(id, Map.class);
//        userModel.forEach((userType, userModel1) -> );
        if (userModel.containsKey(ADMIN)) ADMIN_USERS.add(userModel.get(ADMIN));
        else COMMON_USERS.add(userModel.get(COMMON));
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserModel resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        String id = getId(extensionContext);
        return (UserModel) extensionContext.getStore(NAMESPACE).get(id, Map.class)
                .values().stream().findFirst().orElseThrow();
    }

    /**
     * @param context by current test
     * @return string value from allure_id annotation
     */
    private String getId(final ExtensionContext context) {
        return context.getRequiredTestMethod().getAnnotation(AllureId.class).value();
    }
}
