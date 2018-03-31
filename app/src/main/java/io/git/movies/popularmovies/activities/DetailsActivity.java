package io.git.movies.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.git.movies.popularmovies.BuildConfig;
import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.utils.URLBuilder;

public class DetailsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    @BindView(R.id.titleTV)
    TextView titleTV;
    @BindView(R.id.plotTV)
    TextView overviewTV;
    @BindView(R.id.avgTV)
    TextView avgTV;
    @BindView(R.id.releaseDateTV)
    TextView releaseDateTV;
    @BindView(R.id.posterIV)
    ImageView posterIV;

    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    private String YouTubeKey = BuildConfig.YoutubeAPIKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateMovieDetailsOnUI();

        playerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);

        playerFragment.initialize(YouTubeKey, this);
    }

    private void populateMovieDetailsOnUI() {
        URLBuilder urlBuilder = new URLBuilder();
        MovieDetails details = getIntent().getExtras().getParcelable("MOVIE_DATA");

        if (details != null) {
            titleTV.setText(getText(R.string.title_tv_default) + details.getTitle());
            overviewTV.setText(getText(R.string.plot_tv_default) + details.getOverview());
            releaseDateTV.setText(getText(R.string.release_date_tv_default) + details.getReleaseDate());
            avgTV.setText(getText(R.string.avg_tv_default) + Double.toString(details.getVoteAverage()));
            Picasso.with(this).load(urlBuilder.buildPosterURL(details.getPosterPath())).into(posterIV);
        } else {
            Toast.makeText(this, "No data to present.", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mPlayer = youTubePlayer;

        if (!b) {
            // TODO: Add GridLayout to show the video list and onClick to load the movie via the key.
            // Example for loading video list
            List videoList = new ArrayList();
            videoList.add("Q0CbN8sfihY");
            videoList.add("-py2awmME8s");
            videoList.add("WcIfbdfZRDQ");
            mPlayer.cueVideos(videoList);
        } else {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mPlayer = null;
    }
}
