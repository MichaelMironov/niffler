package niffler.jupiter.di.friend;

import io.qameta.allure.AllureId;
import niffler.api.friend.FriendsClient;
import niffler.data.json.FriendJson;
import niffler.data.json.UserJson;
import niffler.jupiter.di.user.User;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static niffler.jupiter.auth.ApiAuthExtension.AUTH_EXTENSION_NAMESPACE;
import static niffler.jupiter.auth.CreateUserExtension.ON_METHOD_USERS_NAMESPACE;

public class FriendsExtension implements BeforeTestExecutionCallback {

    FriendsClient friendsClient = new FriendsClient();

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        User user = Arrays.stream(context.getRequiredTestMethod().getParameters())
                .filter(parameter -> parameter.isAnnotationPresent(User.class))
                .map(parameter -> parameter.getAnnotation(User.class))
                .findFirst().orElseThrow();

        if (user.friend()) {

            UserJson authUser = context.getStore(AUTH_EXTENSION_NAMESPACE).get(getTestId(context), UserJson.class);
            UserJson friendUser = context.getStore(ON_METHOD_USERS_NAMESPACE).get(getTestId(context), UserJson.class);

            addFriendToUser(authUser, friendUser);

            acceptFriend(friendUser, authUser);
        }
    }

    private void acceptFriend(UserJson friendUser, UserJson user) throws IOException {
        FriendJson userToFriend = new FriendJson();
        userToFriend.setUsername(user.getUserName());
        friendsClient.acceptInvitation(friendUser.getUserName(), userToFriend);
    }

    private void addFriendToUser(UserJson authUser, UserJson friendUser) throws IOException {
        FriendJson friendJson = new FriendJson();
        friendJson.setUsername(friendUser.getUserName());
        friendsClient.addFriend(authUser.getUserName(), friendJson);
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(AllureId.class)
        ).value();
    }
}
