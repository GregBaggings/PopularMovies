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
import io.git.movies.popularmovies.contentProvider.QueryHelper;
import io.git.movies.popularmovies.fragments.ReviewListFragment;
import io.git.movies.popularmovies.fragments.VideoListFragment;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.pojos.Reviews;
import io.git.movies.popularmovies.pojos.VideoList;
import io.git.movies.popularmovies.utils.URLBuilder;

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

    private QueryHelper queryHelper = new QueryHelper();
    private ContentResolver resolver;
    private MovieDetails details;
    private Uri uri = FavoritesContract.FavoritesEntry.CONTENT_URI;
    private int yellow = Color.YELLOW;
    private int gray = Color.GRAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        resolver = this.getContentResolver();
        setSupportActionBar(toolbar);

        populateMovieDetailsOnUI();
    }

    private void populateMovieDetailsOnUI() {
        URLBuilder urlBuilder = new URLBuilder();
        details = getIntent().getExtras().getParcelable("MOVIE_DATA");
        VideoList videoDetails = getIntent().getExtras().getParcelable("TRAILER_DATA");
        Reviews reviews = getIntent().getExtras().getParcelable("REVIEW_DATA");

        Bundle bundle = new Bundle();
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

        if (queryHelper.dataCheck(FavoritesContract.FavoritesEntry.TABLE_NAME, FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, Integer.toString(details.getId()), getApplicationContext())) {
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

            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, details.getId());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_NAME, details.getOriginalTitle());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_POSTER_URL, details.getPosterPath());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RELEASE_DATE, details.getReleaseDate());
            values.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RATING, details.getVoteAverage());


            resolver.insert(uri, values);
            favorite.setSelected(true);
            favorite.setColorFilter(yellow);

            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
        } else if (favorite.isSelected()) {
            String selection = FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + "=?";
            String[] selectionArgs = {String.valueOf(details.getId())};

            resolver.delete(uri, selection, selectionArgs);
            favorite.setSelected(false);
            favorite.setColorFilter(gray);

            Toast.makeText(getApplicationContext(), "Removed", Toast.LENGTH_SHORT).show();
        }
    }
}
