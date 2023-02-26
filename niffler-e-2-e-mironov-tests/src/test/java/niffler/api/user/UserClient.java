package niffler.api.user;

import niffler.api.spec.RestService;
import niffler.data.json.UserJson;

import java.io.IOException;

public class UserClient extends RestService {

    public UserClient() {
        super(CFG.userdataUrl());
    }

    private final UserApi userApi = retrofit.create(UserApi.class);

    public UserJson updateUser(UserJson json) throws IOException {
        return userApi.updateUserInfo(json)
                .execute()
                .body();
    }

    public UserJson getCurrentUser(String username) throws Exception {
        return userApi.currentUser(username)
                .execute()
                .body();
    }
}
