package io.git.movies.popularmovies.pojos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PopularResponse implements Serializable {
    @SerializedName("page")
    int page;
    @SerializedName("total_results")
    int totalResults;
    @SerializedName("total_pages")
    int totalPages;
    @SerializedName("results")
    List<MovieDetails> resultsList;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<MovieDetails> getResultsList() {
        return resultsList;
    }

    public void setResultsList(List<MovieDetails> resultsList) {
        this.resultsList = resultsList;
    }
}
