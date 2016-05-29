package xyz.oguzcelik.hipoandroidchallenge.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.oguzcelik.hipoandroidchallenge.model.Post;

/**
 * Created by Cynapsis on 5/18/2016.
 */
public interface InstagramApi {
    @GET("/v1/tags/{tag-name}/media/recent")
    Call<Post> getResponse(@Path("tag-name") String tagName
            , @Query("client_id") String accessToken);

    @GET("/v1/tags/{tag-name}/media/recent")
    Call<Post> getPaginatedResponse(@Path("tag-name") String tagName
            , @Query("client_id") String accessToken
            , @Query("max_tag_id") String maxTagId);

}
