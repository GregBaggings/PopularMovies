package io.git.movies.popularmovies.rest;


import java.util.List;

import io.git.movies.popularmovies.pojos.MoviesList;
import io.git.movies.popularmovies.pojos.VideoList;

public interface AsyncEventListener {
    void onSuccessMovies(List<MoviesList> movies);
    void onSuccessTrailers(VideoList videos);
    void onFailure(Exception e);
}
