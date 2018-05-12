package io.git.movies.popularmovies.activities;

import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.contentProvider.FavoritesContract;
import io.git.movies.popularmovies.fragments.ReviewListFragment;
import io.git.movies.popularmovies.fragments.VideoListFragment;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.pojos.Reviews;
import io.git.movies.popularmovies.pojos.VideoList;
import io.git.movies.popularmovies.utils.QueryHelper;
import io.git.movies.popularmovies.utils.URLBuilder;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class DetailsActivity extends AppCompatActivity {
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
    @BindView(R.id.favorite)
    ImageButton favorite;
    Bundle bundle = new Bundle();

    private QueryHelper queryHelper = new QueryHelper();
    private ContentResolver resolver;
    private MovieDetails movieDetails;
    private VideoList videoDetails;
    private Reviews reviews;
    private Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI;
    private int yellow = Color.YELLOW;
    private int gray = Color.GRAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);

        if (savedInstanceState != null) {
            movieDetails = savedInstanceState.getParcelable("movieDetails");
            videoDetails = savedInstanceState.getParcelable("videoDetails");
            reviews = savedInstanceState.getParcelable("reviews");
        }

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        resolver = this.getContentResolver();
        setSupportActionBar(toolbar);

        populateMovieDetailsOnUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movieDetails", movieDetails);
        outState.putParcelable("videoDetails", videoDetails);
        outState.putParcelable("reviews", reviews);
        super.onSaveInstanceState(outState);
    }

    private void populateMovieDetailsOnUI() {
        URLBuilder urlBuilder = new URLBuilder();
        movieDetails = getIntent().getExtras().getParcelable("MOVIE_DATA");
        videoDetails = getIntent().getExtras().getParcelable("TRAILER_DATA");
        reviews = getIntent().getExtras().getParcelable("REVIEW_DATA");


        bundle.putParcelable("trailers", videoDetails);
        bundle.putParcelable("reviews", reviews);
        VideoListFragment videoListFragment = new VideoListFragment();
        videoListFragment.setArguments(bundle);
        ReviewListFragment reviewListFragment = new ReviewListFragment();
        reviewListFragment.setArguments(bundle);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.videoFragmentPlaceholder, videoListFragment);
        ft.replace(R.id.reviewListFragmentPlaceholder, reviewListFragment);
        ft.commit();

        if (movieDetails != null) {
            titleTV.setText(getText(R.string.title_tv_default) + movieDetails.getTitle());
            overviewTV.setText(getText(R.string.plot_tv_default) + movieDetails.getOverview());
            releaseDateTV.setText(getText(R.string.release_date_tv_default) + movieDetails.getReleaseDate());
            avgTV.setText(getText(R.string.avg_tv_default) + Double.toString(movieDetails.getVoteAverage()));
            Picasso.with(this).load(urlBuilder.buildPosterURL(movieDetails.getPosterPath())).into(posterIV);
        } else {
            Toast.makeText(this, "No data to present.", Toast.LENGTH_LONG).show();
            finish();
        }

        if (queryHelper.dataCheck(FavoritesContract.FavoritesEntry.TABLE_NAME, FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, Integer.toString(movieDetails.getId()), getApplicationContext())) {
            favorite.setColorFilter(yellow);
            favorite.setSelected(true);
        } else {
            favorite.setColorFilter(gray);
        }
    }

    @OnClick(R.id.favorite)
    public void addAndRemoveMovieFromFavorites() {
        if (!favorite.isSelected()) {
            ContentValues values = new ContentValues();

            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, movieDetails.getId());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_NAME, movieDetails.getOriginalTitle());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_POSTER_URL, movieDetails.getPosterPath());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RELEASE_DATE, movieDetails.getReleaseDate());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RATING, movieDetails.getVoteAverage());


            resolver.insert(uri, values);
            favorite.setSelected(true);
            favorite.setColorFilter(yellow);

            Toast.makeText(getApplicationContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
        } else if (favorite.isSelected()) {
            String selection = FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + "=?";
            String[] selectionArgs = {String.valueOf(movieDetails.getId())};

            resolver.delete(uri, selection, selectionArgs);
            favorite.setSelected(false);
            favorite.setColorFilter(gray);

            Toast.makeText(getApplicationContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();
        }
    }
}
