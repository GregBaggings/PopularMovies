package io.git.movies.popularmovies.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.pojos.Reviews;

public class ReviewListFragment extends ListFragment {

    private List reviewComments = new ArrayList<>();
    private Reviews reviews;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            reviews = getArguments().getParcelable("reviews");
            Log.i("TESZT", "Reviews from Bundle: " + reviews);
            getReviewItems(reviews);
            Log.i("TESZT", "Review comments: " + reviewComments);
        }
        View view = inflater.inflate(R.layout.reviews_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, reviewComments);
        setListAdapter(adapter);
    }

    public List<String> getReviewItems(Reviews reviews) {
        for (int i = 0; i < reviews.getReviewItemList().size(); i++) {
            reviewComments.add(reviews.getReviewItemList().get(i).getContent());
        }
        return reviewComments;
    }
}
