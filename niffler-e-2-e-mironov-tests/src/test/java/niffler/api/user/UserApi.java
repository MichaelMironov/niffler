package niffler.api.user;

import niffler.data.json.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/updateUserInfo")
    Call<UserJson> updateUserInfo(@Body UserJson userJson);

    @GET("/currentUser")
    Call<UserJson> currentUser(@Query("username") String username);
}
