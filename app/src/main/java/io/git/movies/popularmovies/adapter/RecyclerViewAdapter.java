package io.git.movies.popularmovies.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.activities.DetailsActivity;
import io.git.movies.popularmovies.pojos.MovieDetails;
import io.git.movies.popularmovies.utils.URLBuilder;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder> {
    private URLBuilder urlBuilder = new URLBuilder();
    private Context myContext;
    private List<MovieDetails> detailsList;
    private ProgressBar myProgressBar;
    private RecyclerView myRecyclerView;

    public RecyclerViewAdapter(Context context, List<MovieDetails> detailsList, RecyclerView recyclerView, ProgressBar progressBar) {
        this.myContext = context;
        this.detailsList = detailsList;
        this.myRecyclerView = recyclerView;
        this.myProgressBar = progressBar;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_row, parent, false);
        ButterKnife.bind(this, itemView);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Picasso.with(myContext)
                .load(urlBuilder.buildPosterURL(detailsList.get(position).getPosterPath()))
                .placeholder(R.drawable.ic_loading).resize(600, 800)
                .error(R.drawable.ic_no_picture).into(holder.posterIV, new Callback() {

            @Override
            public void onSuccess() {
                myProgressBar.setVisibility(View.GONE);
                myRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return detailsList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.posterIV)
        ImageView posterIV;
        private List<MovieDetails> list;
        private Context context;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            list = detailsList;
            context = myContext;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Intent intent = new Intent(context, DetailsActivity.class);
                    intent.putExtra("MOVIE_DATA", list.get(index));
                    context.startActivity(intent);
                }
            });
        }
    }
}
