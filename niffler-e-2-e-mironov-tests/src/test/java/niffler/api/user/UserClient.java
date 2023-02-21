package niffler.api.user;

import niffler.api.spec.Specification;
import niffler.data.json.UserJson;

import java.io.IOException;

public class UserClient {

    private final UserService userService = Specification
            .request(UserService.baseUrl)
            .create(UserService.class);

    public UserJson updateUser(UserJson json) throws IOException {
        return userService.updateUserInfo(json).execute().body();
    }

    public UserJson getCurrentUser(String username) throws Exception {
        return userService.currentUser(username)
                .execute()
                .body();
    }
}
