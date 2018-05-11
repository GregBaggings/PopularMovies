package io.git.movies.popularmovies.utils;

import android.util.Log;

public class URLBuilder {
    final private String baseURL = "http://image.tmdb.org/t/p/";
    final private String size = "w500";

    public String buildPosterURL(String pathFromResponse) {
        String posterURL = baseURL + size + pathFromResponse;
        Log.i("TESZT", "The poster url is " + posterURL);
        return posterURL;
    }
}
