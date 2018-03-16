package io.git.movies.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import io.git.movies.popularmovies.pojos.MovieDetails;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MovieDetails details = (MovieDetails) getIntent().getSerializableExtra("MOVIE_DATA");

        TextView titleTV = findViewById(R.id.textView);
        titleTV.setText(details.getTitle());
    }

}
