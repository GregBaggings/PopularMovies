package io.git.movies.popularmovies.rest;

import io.git.movies.popularmovies.BuildConfig;
import io.git.movies.popularmovies.pojos.PopularResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPIInterface {

    String apiKey = BuildConfig.MoviesAPIKey;

    @GET("http://api.themoviedb.org/3/movie/popular")
    Call<PopularResponse> getPopularMoviesResponse(@Query("api_key") String apiKey);

}
