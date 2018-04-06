package io.git.movies.popularmovies.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import io.git.movies.popularmovies.pojos.MoviesList;
import retrofit2.Call;
import retrofit2.Response;

public class AsyncMoviesRequestHandler extends AsyncTask<Call, Void, MoviesList> {

    private List list;
    private AsyncEventListener asyncEventListener;
    private Call<MoviesList> myCall;
    private Context myContext;

    public AsyncMoviesRequestHandler(Call call, Context context, AsyncEventListener eventListener) {
        myCall = call;
        myContext = context;
        asyncEventListener = eventListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected MoviesList doInBackground(Call... params) {
        try {
            myCall = params[0];
            Response<MoviesList> response = myCall.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(MoviesList result) {
        if (result != null) {
            list = result.getResultsList();
            if (asyncEventListener != null) {
                asyncEventListener.onSuccessMovies(list);
            }
        } else {
            Toast.makeText(myContext, "No result", Toast.LENGTH_SHORT).show();
        }
    }
}

