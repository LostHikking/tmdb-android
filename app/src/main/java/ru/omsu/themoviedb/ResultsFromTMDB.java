package ru.omsu.themoviedb;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultsFromTMDB {
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
    public List<Result> results;

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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
