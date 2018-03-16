package io.git.movies.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.git.movies.popularmovies.pojos.MovieDetails;

public class PosterAdapter extends BaseAdapter {
    URLBuilder urlBuilder = new URLBuilder();
    private final Context context;
    private final List<MovieDetails> detailsList;

    public PosterAdapter(Context context, List<MovieDetails> detailsList) {
        this.context = context;
        this.detailsList = detailsList;
    }

    @Override
    public int getCount() {
        Log.i("ADAPTER", "Adapter size: " + detailsList.size() + "");
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
        Picasso.with(context).load(urlBuilder.buildPosterURL(detailsList.get(i).getPosterPath())).into(imageView);
        return imageView;
    }
}
