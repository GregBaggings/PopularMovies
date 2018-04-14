package io.git.movies.popularmovies.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import io.git.movies.popularmovies.R;
import io.git.movies.popularmovies.pojos.VideoList;

public class VideoListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    String dummy[] = {"a","b","c"};
    VideoList videoList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            videoList = getArguments().getParcelable("trailers");
            Log.i("TESZT", "Trailers from Bundle: " + videoList);
        }
        View view = inflater.inflate(R.layout.videos_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dummy);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "Item: " + i, Toast.LENGTH_SHORT).show();
    }
}
