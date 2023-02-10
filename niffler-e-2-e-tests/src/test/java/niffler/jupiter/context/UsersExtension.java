package niffler.jupiter.context;

import io.qameta.allure.AllureId;
import niffler.jupiter.context.User.UserType;
import niffler.model.UserModel;
import org.junit.jupiter.api.extension.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        COMMON_USERS.add(new UserModel("test", "12345"));
        COMMON_USERS.add(new UserModel("test", "12345"));
        COMMON_USERS.add(new UserModel("test", "12345"));
    }

    /**
     * Getting user from USERS_QUEUE with waiting in while cycle until he wasn't null
     */
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        String id = getId(context);
        List<UserModel> users = new ArrayList<>();
        List<UserType> userTypes = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(p -> p.isAnnotationPresent(User.class))
                .map(p -> p.getAnnotation(User.class))
                .map(User::value).toList();

        int length = context.getRequiredTestMethod().getParameters().length;
        List<Map<UserType, UserModel>> maps = new ArrayList<>();

        while (users.size() < length) {
            for (int i = 0; i < length; i++) {
                users.add(userTypes.get(i) == ADMIN ? ADMIN_USERS.poll() : COMMON_USERS.poll());
                maps.add(Map.of(userTypes.get(i), users.get(i)));
            }
        }
        context.getStore(NAMESPACE).put(id, maps);

    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterTestExecution(final ExtensionContext context) throws Exception {
        String id = getId(context);

        List<Map<UserType, UserModel>> users = context.getStore(NAMESPACE).get(id, List.class);

        final Set<Map.Entry<UserType, UserModel>> collect = users.stream().map(Map::entrySet)
                .map(entries -> entries.stream().iterator().next()).collect(Collectors.toSet());

        if (collect.iterator().next().getKey() == ADMIN) {
            ADMIN_USERS.add(collect.iterator().next().getValue());
        } else {
            COMMON_USERS.add(collect.iterator().next().getValue());
        }
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserModel.class)
                && parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserModel resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        String id = getId(extensionContext);

        List<Map<UserType, UserModel>> users = extensionContext.getStore(NAMESPACE).get(id, List.class);

        UserType userType = parameterContext.getParameter().getAnnotation(User.class).value();

        return users.stream().map(Map::entrySet).filter(entries -> entries.stream().iterator().next().getKey() == userType)
                .map(entries -> entries.iterator().next().getValue()).findFirst().orElseThrow();

    }

    /**
     * @param context by current test
     * @return string value from allure_id annotation
     */
    private String getId(final ExtensionContext context) {
        return context.getRequiredTestMethod().getAnnotation(AllureId.class).value();
    }
}
