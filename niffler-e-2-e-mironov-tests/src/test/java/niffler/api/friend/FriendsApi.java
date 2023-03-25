package niffler.api.friend;

import niffler.data.json.FriendJson;
import niffler.data.json.UserJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface FriendsApi {

    @GET("/friends")
    public Call<List<UserJson>> friends(@Query("username") String username,
                                        @Query("includePending") boolean includePending);

    @GET("/invitations")
    public Call<List<UserJson>> invitations(@Query("username") String username);


    @POST("/acceptInvitation")
    public Call<Void> acceptInvitation(@Query("username") String username,
                                           @Body FriendJson invitation);

    @POST("/declineInvitation")
    public Call<UserJson> declineInvitation(@Query("username") String username,
                                            @Body FriendJson invitation);

    @POST("/addFriend")
    public Call<Void> addFriend(@Query("username") String username,
                          @Body FriendJson friend);

    @DELETE("/removeFriend")
    public Call<List<UserJson>> removeFriend(@Query("username") String username,
                                       @Query("friendUsername") String friendUsername);
}
