package io.git.movies.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.adapter.PosterAdapter;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.pojos.MoviesList;
import io.git.movies.popularmovies.rest.AsyncEventListener;
import io.git.movies.popularmovies.rest.AsyncRequestHandler;
import io.git.movies.popularmovies.rest.MoviesAPI;
import io.git.movies.popularmovies.rest.MoviesAPIInterface;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private PosterAdapter adapter;
    private GridView gridView;
    private List<MovieDetails> list = new ArrayList<>();
    private MoviesAPIInterface service = MoviesAPI.getRetrofit().create(MoviesAPIInterface.class);
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = null;

        loadingIndicator = findViewById(R.id.loading_spinner);
        gridView = findViewById(R.id.postersGridView);

        if (checkInternetConnection()) {
            loadingIndicator.setVisibility(View.VISIBLE);
            Call<MoviesList> call = service.getPopularMovies(MoviesAPIInterface.apiKey);
            getResponse(call);
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("MOVIE_DATA", list.get(position));
                startActivity(intent);
            }
        });
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private void getResponse(Call call) {
        list.clear();
        AsyncRequestHandler requestHandler = new AsyncRequestHandler(call, getApplicationContext(), new AsyncEventListener() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(List movies) {
                adapter = new PosterAdapter(getApplicationContext(), movies);
                gridView.setAdapter(adapter);
                loadingIndicator.setVisibility(View.GONE);
                list.addAll(movies);
            }
        });

        requestHandler.execute(call);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_most_popular) {
            gridView.setAdapter(null);
            Call<MoviesList> call = service.getPopularMovies(MoviesAPIInterface.apiKey);
            getResponse(call);
        }
        if (id == R.id.action_top_rated) {
            gridView.setAdapter(null);
            Call<MoviesList> call = service.getTopRatedMovies(MoviesAPIInterface.apiKey);
            getResponse(call);
        }

        return super.onOptionsItemSelected(item);
    }
}


