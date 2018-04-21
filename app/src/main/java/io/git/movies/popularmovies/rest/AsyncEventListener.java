package io.git.movies.popularmovies.rest;


import java.util.List;

import io.git.movies.popularmovies.pojos.MoviesList;
import io.git.movies.popularmovies.pojos.Reviews;
import io.git.movies.popularmovies.pojos.VideoList;

public interface AsyncEventListener {
    void onSuccessMovies(List<MoviesList> movies);
    void onSuccessTrailers(VideoList videos);
    void onSuccessReviews(Reviews reviews);
    void onFailure(Exception e);
}
