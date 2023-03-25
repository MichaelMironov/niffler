package niffler.jupiter.di.friend;

import io.qameta.allure.AllureId;
import niffler.api.friend.FriendsClient;
import niffler.config.Config;
import niffler.data.json.FriendJson;
import niffler.database.dao.PostgresHibernateUsersDAO;
import niffler.database.entity.auth.Authority;
import niffler.database.entity.auth.AuthorityEntity;
import niffler.database.entity.auth.UserEntity;
import org.junit.jupiter.api.extension.*;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.*;

import static niffler.utils.DataUtils.generateRandomPassword;
import static niffler.utils.DataUtils.generateRandomUsername;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class CreateFriendExtension implements ParameterResolver, BeforeEachCallback {

    private final PostgresHibernateUsersDAO postgresHibernateUsersDAO = new PostgresHibernateUsersDAO();
    private final FriendsClient friendsClient = new FriendsClient();
    protected static final Config CFG = Config.getConfig();

    public static ExtensionContext.Namespace PEOPLES_NAMESPACE = ExtensionContext.Namespace.create(CreateFriendExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {

        String testId = getTestId(context);
        Parameter[] parameters = context.getRequiredTestMethod().getParameters();

        List<Friend> friends = Arrays.stream(parameters).map(parameter -> parameter.getAnnotation(Friend.class)).toList();

        List<UserEntity> users = new ArrayList<>();

        for (Friend friend : friends) {

            String username = friend.username().isEmpty() ? generateRandomUsername() : friend.username();
            String password = friend.password().isEmpty() ? generateRandomPassword() : friend.password();

            UserEntity userEntity = UserEntity.builder()
                    .username(username)
                    .password(createDelegatingPasswordEncoder().encode(password))
                    .enabled(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .accountNonExpired(true)
                    .build();

            userEntity.setAuthorities(Set.of(
                    AuthorityEntity.builder().user(userEntity).authority(Authority.read).build(),
                    AuthorityEntity.builder().user(userEntity).authority(Authority.write).build()));

            users.add(userEntity);

            postgresHibernateUsersDAO.merge(userEntity);
            userEntity.setPassword(password);
            context.getStore(PEOPLES_NAMESPACE).put(testId, users);
        }

        addFriendToUser(users.get(0), users.get(1));
        acceptFriend(users.get(1), users.get(0));

        System.out.println();
    }

    private void acceptFriend(UserEntity friendUser, UserEntity user) throws IOException {
        FriendJson userToFriend = new FriendJson();
        userToFriend.setUsername(user.getUsername());
        friendsClient.acceptInvitation(friendUser.getUsername(), userToFriend);
    }

    private void addFriendToUser(UserEntity authUser, UserEntity friendUser) throws IOException {
        FriendJson friendJson = new FriendJson();
        friendJson.setUsername(friendUser.getUsername());
        friendsClient.addFriend(authUser.getUsername(), friendJson);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return true;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return null;
    }

    private String getTestId(ExtensionContext context) {
        return Objects.requireNonNull(
                context.getRequiredTestMethod().getAnnotation(AllureId.class)
        ).value();
    }
}
