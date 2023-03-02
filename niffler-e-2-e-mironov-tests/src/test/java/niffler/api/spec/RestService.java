package niffler.api.spec;

import niffler.config.Config;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class RestService {

    protected static final Config CFG = Config.getConfig();

    protected final Retrofit retrofit;

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(BODY)).build();

    protected RestService(String restServiceUrl) {
        this.retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(restServiceUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

    }
}