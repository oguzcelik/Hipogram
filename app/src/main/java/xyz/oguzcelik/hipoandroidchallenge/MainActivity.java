package xyz.oguzcelik.hipoandroidchallenge;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.oguzcelik.hipoandroidchallenge.model.Datum;
import xyz.oguzcelik.hipoandroidchallenge.model.Post;
import xyz.oguzcelik.hipoandroidchallenge.service.InstagramRestAdapter;

public class MainActivity extends AppCompatActivity implements Callback<Post> {
    private static final String ACCESS_TOKEN = "YOUR_ACCESS_TOKEN";
    private final int GRID_SIZE = 3;

    private String lastQuery;
    private List<Datum> data;
    private boolean isNewSearch = true;
    private String maxTagId;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        final GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getApplicationContext(),GRID_SIZE);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(gridLayoutManager.findLastCompletelyVisibleItemPosition()
                        == gridLayoutManager.getItemCount() - 1) {
                    loadPhotosForTag(lastQuery);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(lastQuery!=null && !lastQuery.equals(query)) {
                    isNewSearch=true;
                }
                loadPhotosForTag(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void loadPhotosForTag(String tag) {
        InstagramRestAdapter instagramRestAdapter = new InstagramRestAdapter();
        Call<Post> call;
        if(lastQuery!= null && lastQuery.equals(tag)) {
            isNewSearch = false;
            call = instagramRestAdapter.getInstagramApiPaginatedResponse(tag,ACCESS_TOKEN,maxTagId);
        } else {
            lastQuery = tag;
            call = instagramRestAdapter.getInstagramApiResponse(tag,ACCESS_TOKEN);
        }
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Post> call, Response<Post> response) {
        if(isNewSearch) {
            data = response.body().getData();
            ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext());
            imageAdapter.setDataList(data);
            recyclerView.setAdapter(imageAdapter);
        } else {
            data.addAll(response.body().getData());
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        maxTagId = response.body().getPaginationNextMaxTagId();
    }

    @Override
    public void onFailure(Call<Post> call, Throwable t) {
        Toast.makeText(getApplicationContext(),"Could'nt load images",Toast.LENGTH_SHORT).show();
        lastQuery="";
    }
}
