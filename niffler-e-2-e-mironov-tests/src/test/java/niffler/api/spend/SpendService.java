package niffler.api.spend;

import niffler.data.json.SpendJson;
import niffler.data.json.UserJson;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SpendService {

    String baseUrl = "http://127.0.0.1:8093/";

    @POST("/addSpend")
    Call<SpendJson> addSpend(@Body SpendJson spend);

}
