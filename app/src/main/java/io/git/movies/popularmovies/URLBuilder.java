package io.git.movies.popularmovies;

import android.util.Log;

public class URLBuilder {
    final String baseURL = "http://image.tmdb.org/t/p/";
    final String size = "w500";

    String buildPosterURL(String pathFromResponse) {
        String posterURL = baseURL + size + pathFromResponse;
        Log.i("URLBuilder", "The poster url is " + posterURL);
        return posterURL;
    }
}
