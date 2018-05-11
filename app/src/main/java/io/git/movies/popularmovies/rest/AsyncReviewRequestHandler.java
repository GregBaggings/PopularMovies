package io.git.movies.popularmovies.rest;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import io.git.movies.popularmovies.pojos.Reviews;
import retrofit2.Call;
import retrofit2.Response;

public class AsyncReviewRequestHandler extends AsyncTask<Call, Void, Reviews> {

    private AsyncEventListener asyncEventListener;
    private Call<Reviews> myCall;
    private Context myContext;

    public AsyncReviewRequestHandler(Call call, Context context, AsyncEventListener eventListener) {
        myCall = call;
        myContext = context;
        asyncEventListener = eventListener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Reviews doInBackground(Call... params) {
        try {
            myCall = params[0];
            Response<Reviews> response = myCall.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Reviews result) {
        if (result != null) {
            if (asyncEventListener != null) {
                asyncEventListener.onSuccessReviews(result);
            }
        } else {
            Toast.makeText(myContext, "No result", Toast.LENGTH_SHORT).show();
        }
    }
}

