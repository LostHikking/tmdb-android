package ru.omsu.themoviedb.TMDB.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesPage {
    @SerializedName("page")
    @Expose
    public long page;
    @SerializedName("total_results")
    @Expose
    public long totalResults;
    @SerializedName("total_pages")
    @Expose
    public long totalPages;
    @SerializedName("results")
    @Expose
    public List<Movie> results;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
