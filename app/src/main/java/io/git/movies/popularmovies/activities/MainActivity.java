package io.git.movies.popularmovies.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.adapter.RecyclerViewAdapter;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.pojos.MoviesList;
import io.git.movies.popularmovies.pojos.VideoList;
import io.git.movies.popularmovies.rest.AsyncEventListener;
import io.git.movies.popularmovies.rest.AsyncMoviesRequestHandler;
import io.git.movies.popularmovies.rest.MoviesAPI;
import io.git.movies.popularmovies.rest.MoviesAPIInterface;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<MovieDetails> list = new ArrayList<>();
    private MoviesAPIInterface service = MoviesAPI.getRetrofit().create(MoviesAPIInterface.class);
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        recyclerViewAdapter = null;
        setRecyclerViewDetails();

        if (checkInternetConnection()) {
            loadingIndicator.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            Call<MoviesList> call = service.getPopularMovies(MoviesAPIInterface.apiKey);
            getResponse(call);
        } else {
            Toast.makeText(getApplicationContext(), "No internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void setRecyclerViewDetails() {
        recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), list, recyclerView, loadingIndicator);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private boolean checkInternetConnection() throws NullPointerException {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }

    private void getResponse(Call call) {
        list.clear();
        loadingIndicator.setVisibility(View.VISIBLE);
        AsyncMoviesRequestHandler requestHandler = new AsyncMoviesRequestHandler(call, getApplicationContext(), new AsyncEventListener() {
            @Override
            public void onSuccessTrailers(VideoList videos) {

            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccessMovies(List movies) {
                recyclerViewAdapter = new RecyclerViewAdapter(getApplicationContext(), movies, recyclerView, loadingIndicator);
                recyclerView.setAdapter(recyclerViewAdapter);
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
            recyclerView.setAdapter(null);
            Call<MoviesList> call = service.getPopularMovies(MoviesAPIInterface.apiKey);
            getResponse(call);
        }
        if (id == R.id.action_top_rated) {
            recyclerView.setAdapter(null);
            Call<MoviesList> call = service.getTopRatedMovies(MoviesAPIInterface.apiKey);
            getResponse(call);
        }

        return super.onOptionsItemSelected(item);
    }
}
