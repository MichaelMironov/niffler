package niffler.api.service;

import niffler.api.friend.FriendsClient;
import niffler.data.json.UserJson;

import java.io.IOException;
import java.util.List;

public class FriendsService {
    FriendsClient friendsClient = new FriendsClient();

    public UserJson getInvitationUserByUsername(String friendUsername, UserJson invitationUser) throws IOException {
        return friendsClient.invitations(friendUsername).stream()
                .filter(users -> users.getUserName().equals(invitationUser.getUserName()))
                .findFirst().get();
    }
}
