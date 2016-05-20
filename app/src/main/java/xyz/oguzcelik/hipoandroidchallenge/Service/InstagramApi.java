package xyz.oguzcelik.hipoandroidchallenge.Service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import xyz.oguzcelik.hipoandroidchallenge.POJO.Post;

/**
 * Created by Cynapsis on 5/18/2016.
 */
public interface InstagramApi {
    @GET("/v1/tags/{tag-name}/media/recent")
    Call<Post> getResponse(@Path("tag-name") String tagName
            , @Query("client_id") String accessToken);
}
