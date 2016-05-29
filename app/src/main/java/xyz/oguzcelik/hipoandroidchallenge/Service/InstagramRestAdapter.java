package xyz.oguzcelik.hipoandroidchallenge.service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.oguzcelik.hipoandroidchallenge.model.Post;

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

    public Call<Post> getInstagramApiResponse(String tag, String accessToken) {
        return api.getResponse(tag,accessToken);
    }

    public Call<Post> getInstagramApiPaginatedResponse(String tag, String accessToken, String maxTagId) {
        return api.getPaginatedResponse(tag,accessToken,maxTagId);
    }


}
