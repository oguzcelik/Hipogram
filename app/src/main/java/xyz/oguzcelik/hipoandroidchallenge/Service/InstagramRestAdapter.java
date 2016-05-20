package xyz.oguzcelik.hipoandroidchallenge.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Cynapsis on 5/18/2016.
 */
public class InstagramRestAdapter {
    protected Retrofit retrofit;
    protected InstagramApi api;

    public InstagramRestAdapter() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.instagram.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(InstagramApi.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public InstagramApi getApi() {
        return api;
    }
}
