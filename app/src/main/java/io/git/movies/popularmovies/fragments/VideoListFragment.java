package io.git.movies.popularmovies.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.pojos.VideoList;

public class VideoListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private List<String> titles = new ArrayList<>();
    private VideoList videoList;
    private Parcelable listViewState;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            videoList = getArguments().getParcelable("trailers");
            Log.i("TESZT", "Trailers from Bundle: " + videoList);
            getTitles(videoList);
        }

        View view = inflater.inflate(R.layout.videos_fragment, container, false);
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            listViewState = savedInstanceState.getParcelable("LIST_STATE");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, titles);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        listViewState = getListView().onSaveInstanceState();
        state.putParcelable("LIST_STATE", listViewState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String videoKey = videoList.getVideoList().get(i).getKey();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoKey));
        startActivity(intent);
    }

    public List<String> getTitles(VideoList videoList) {
        for (int i = 0; i < videoList.getVideoList().size(); i++) {
            titles.add(videoList.getVideoList().get(i).getName());
        }
        return titles;
    }
}
