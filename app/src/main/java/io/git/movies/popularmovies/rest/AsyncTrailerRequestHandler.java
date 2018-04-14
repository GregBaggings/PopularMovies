package io.git.movies.popularmovies.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import io.git.movies.popularmovies.pojos.VideoList;
import retrofit2.Call;
import retrofit2.Response;

public class AsyncTrailerRequestHandler extends AsyncTask<Call, Void, VideoList> {

    private List list;
    private AsyncEventListener asyncEventListener;
    private Call<VideoList> myCall;
    private Context myContext;

    public AsyncTrailerRequestHandler(Call call, Context context, AsyncEventListener eventListener) {
        myCall = call;
        myContext = context;
        asyncEventListener = eventListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected VideoList doInBackground(Call... params) {
        try {
            myCall = params[0];
            Response<VideoList> response = myCall.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(VideoList result) {
        if (result != null) {
            if (asyncEventListener != null) {
                asyncEventListener.onSuccessTrailers(result);
            }
        } else {
            Toast.makeText(myContext, "No result", Toast.LENGTH_SHORT).show();
        }
    }
}

