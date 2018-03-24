package io.git.movies.popularmovies.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.utils.URLBuilder;

public class PosterAdapter extends BaseAdapter {
    private final static String LOG_TAG = PosterAdapter.class.getSimpleName();
    private URLBuilder urlBuilder = new URLBuilder();
    private final Context context;
    private final List<MovieDetails> detailsList;

    public PosterAdapter(Context context, List<MovieDetails> detailsList) {
        this.context = context;
        this.detailsList = detailsList;
    }

    @Override
    public int getCount() {
        Log.i(LOG_TAG, "Adapter size: " + detailsList.size() + "");
        return detailsList.size();
    }

    @Override
    public Object getItem(int i) {
        return detailsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        Picasso.with(context)
                .load(urlBuilder.buildPosterURL(detailsList.get(i).getPosterPath()))
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_no_picture).into(imageView);

        return imageView;
    }
}
