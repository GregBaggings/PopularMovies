package io.git.movies.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.pojos.PopularResponse;
import io.git.movies.popularmovies.rest.MoviesAPI;
import io.git.movies.popularmovies.rest.MoviesAPIInterface;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    PosterAdapter adapter;
    GridView gridView;
    List<MovieDetails> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView = findViewById(R.id.postersGridView);

        MoviesAPIInterface service = MoviesAPI.getRetrofit().create(MoviesAPIInterface.class);
        final Call<PopularResponse> call = service.getPopularMoviesResponse(MoviesAPIInterface.apiKey);
        new GetPopularMovies().execute(call);

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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class GetPopularMovies extends AsyncTask<Call, Void, PopularResponse> {
        private ProgressBar loadingIndicator;

        @Override
        protected void onPreExecute() {
            adapter = (PosterAdapter) gridView.getAdapter();
            loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected PopularResponse doInBackground(Call... params) {
            try {
                Call<PopularResponse> call = params[0];
                Response<PopularResponse> response = call.execute();
                return response.body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(PopularResponse result) {
            list = result.getResultsList();
            adapter = new PosterAdapter(getApplicationContext(), list);
            gridView.setAdapter(adapter);

            // Would like to add a check here to make sure that the UI is ready to show after the adapter setting
            // (in the adapter getView I auto create the ImageViews and load them with the image via Picasso)
            loadingIndicator.setVisibility(View.GONE);
        }
    }
}


