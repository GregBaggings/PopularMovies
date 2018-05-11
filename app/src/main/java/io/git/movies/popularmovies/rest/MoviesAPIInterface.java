package io.git.movies.popularmovies.rest;

import io.git.movies.popularmovies.BuildConfig;
import io.git.movies.popularmovies.pojos.MoviesList;
import io.git.movies.popularmovies.pojos.Reviews;
import io.git.movies.popularmovies.pojos.VideoList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesAPIInterface {

    String apiKey = BuildConfig.MoviesAPIKey;

    @GET("http://api.themoviedb.org/3/movie/popular")
    Call<MoviesList> getPopularMovies(@Query("api_key") String apiKey);

    @GET("https://api.themoviedb.org/3/movie/top_rated")
    Call<MoviesList> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("https://api.themoviedb.org/3/movie/{id}/videos")
    Call<VideoList> getTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("https://api.themoviedb.org/3/movie/{movieId}/reviews")
    Call<Reviews> getReviews(@Path("movieId") int id, @Query("api_key") String apiKey);
}
