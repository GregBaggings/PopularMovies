package io.git.movies.popularmovies.rest;


import java.util.List;

import io.git.movies.popularmovies.pojos.MoviesList;

public interface AsyncEventListener {
    void onSuccess(List<MoviesList> movies);
    void onFailure(Exception e);
}
