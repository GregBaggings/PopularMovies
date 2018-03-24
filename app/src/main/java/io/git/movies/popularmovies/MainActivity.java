package io.git.movies.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.pojos.MoviesList;
import io.git.movies.popularmovies.rest.MoviesAPI;
import io.git.movies.popularmovies.rest.MoviesAPIInterface;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    PosterAdapter adapter;
    GridView gridView;
    List<MovieDetails> list = new ArrayList<>();
    MoviesAPIInterface service = MoviesAPI.getRetrofit().create(MoviesAPIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.postersGridView);

        Call<MoviesList> call = service.getPopularMovies(MoviesAPIInterface.apiKey);
        new GetMovies().execute(call);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("MOVIE_DATA", list.get(position));
                startActivity(intent);
            }
        });
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
            new GetMovies().execute(call);
        }
        if (id == R.id.action_top_rated) {
            gridView.setAdapter(null);
            Call<MoviesList> call = service.getTopRatedMovies(MoviesAPIInterface.apiKey);
            new GetMovies().execute(call);
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetMovies extends AsyncTask<Call, Void, MoviesList> {
        private ProgressBar loadingIndicator;

        @Override
        protected void onPreExecute() {
            adapter = (PosterAdapter) gridView.getAdapter();
            loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MoviesList doInBackground(Call... params) {
            try {
                Call<MoviesList> call = params[0];
                Response<MoviesList> response = call.execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MoviesList result) {
            list = result.getResultsList();
            adapter = new PosterAdapter(getApplicationContext(), list);
            gridView.setAdapter(adapter);
            // if(adapter.isUIReady()){
            loadingIndicator.setVisibility(View.GONE);
            //  }
        }
    }
}


