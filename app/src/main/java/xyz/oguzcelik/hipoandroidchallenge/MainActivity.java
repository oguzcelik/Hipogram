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
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.oguzcelik.hipoandroidchallenge.POJO.Post;
import xyz.oguzcelik.hipoandroidchallenge.Service.InstagramRestAdapter;

public class MainActivity extends AppCompatActivity implements Callback<Post> {
    RecyclerView recyclerView;
    private static final String ACCESS_TOKEN = "8b197f774ece48b2b429ae1f542719a7";
    TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        testText = (TextView) findViewById(R.id.testText);
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
                // will do something
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
        Call<Post> call = instagramRestAdapter.getApi().getResponse(tag,ACCESS_TOKEN);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Post> call, Response<Post> response) {
        Toast.makeText(getApplicationContext(),"Call successfull !",Toast.LENGTH_SHORT).show();
        testText.setText(""+response.body().getData().get(0).getImages().getLowResolution().getUrl()
        );
    }

    @Override
    public void onFailure(Call<Post> call, Throwable t) {

    }
}
