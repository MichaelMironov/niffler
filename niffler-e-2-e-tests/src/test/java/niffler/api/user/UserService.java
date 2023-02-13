package niffler.api.user;

import niffler.data.json.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

    String baseUrl = "http://127.0.0.1:8089";

    @POST("/updateUserInfo")
    Call<UserJson> updateUserInfo(@Body UserJson userJson);
}
