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

import java.util.ArrayList;
import java.util.List;

import io.git.movies.popularmovies.R;

public class VideoListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    String[] dummyItems = {"Test1", "Test2", "Test3", "Test4", "Test5"};
    List list = new ArrayList();

   /* public static VideoListFragment newInstance(VideoList videoDetails) {
        VideoListFragment fragment = new VideoListFragment();
        Bundle args = new Bundle();
        args.putParcelable("trailers", videoDetails);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videos_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            list = getArguments().getParcelable("trailers");
            Log.i("TESZT", "Content from the details activity: " + list.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dummyItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), "Item: " + i, Toast.LENGTH_SHORT).show();
    }
}
