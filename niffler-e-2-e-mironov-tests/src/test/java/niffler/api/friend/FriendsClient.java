package niffler.api.friend;

import niffler.api.spec.RestService;
import niffler.data.json.FriendJson;
import niffler.data.json.UserJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class FriendsClient extends RestService {

    public FriendsClient() {
        super(CFG.userdataUrl());
    }

    private static final Logger LOG = LoggerFactory.getLogger(FriendsClient.class);

    private final FriendsApi friendsApi = retrofit.create(FriendsApi.class);

    public List<UserJson> friends(String username, boolean pending) throws IOException {
        return friendsApi.friends(username, pending).execute().body();
    }

    public List<UserJson> invitations(String username) throws IOException {

        return friendsApi.invitations(username).execute().body();
    }

    public void acceptInvitation(String username, FriendJson invitation) throws IOException {
        friendsApi.acceptInvitation(username, invitation).execute();
    }

    public List<UserJson> declineInvitation(String username, FriendJson invitation) {
        return null;
    }

    public void addFriend(String username, FriendJson friend) throws IOException {

        friendsApi.addFriend(username, friend).execute();
    }

    public List<UserJson> removeFriend(String username, String friendUsername) throws IOException {
        return friendsApi.removeFriend(username, friendUsername).execute().body();
    }
}
