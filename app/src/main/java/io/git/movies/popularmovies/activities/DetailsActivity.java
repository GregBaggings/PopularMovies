package io.git.movies.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.utils.URLBuilder;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateMovieDetailsOnUI();
    }

    private void populateMovieDetailsOnUI() {
        URLBuilder urlBuilder = new URLBuilder();
        MovieDetails details = getIntent().getExtras().getParcelable("MOVIE_DATA");

        TextView titleTV = findViewById(R.id.titleTV);
        TextView overviewTV = findViewById(R.id.plotTV);
        TextView avgTV = findViewById(R.id.avgTV);
        TextView releaseDateTV = findViewById(R.id.releaseDateTV);
        ImageView posterIV = findViewById(R.id.posterIV);

        titleTV.setText(getText(R.string.title_tv_default) + details.getTitle());
        overviewTV.setText(getText(R.string.plot_tv_default) + details.getOverview());
        releaseDateTV.setText(getText(R.string.release_date_tv_default) + details.getReleaseDate());
        avgTV.setText(getText(R.string.avg_tv_default) + Double.toString(details.getVoteAverage()));
        Picasso.with(this).load(urlBuilder.buildPosterURL(details.getPosterPath())).into(posterIV);
    }
}
