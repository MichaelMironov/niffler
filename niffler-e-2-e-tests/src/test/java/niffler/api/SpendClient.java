package niffler.api;

import niffler.api.spend.SpendService;
import niffler.data.json.SpendJson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class SpendClient {

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(BODY)).build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SpendService.baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private final SpendService spendService = retrofit.create(SpendService.class);

    public SpendJson createSpendJson(SpendJson json) throws IOException {
        return spendService.addSpend(json).execute().body();
    }
}
