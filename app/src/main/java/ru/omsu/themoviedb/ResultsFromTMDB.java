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
    public List<Result> results = null;
}
