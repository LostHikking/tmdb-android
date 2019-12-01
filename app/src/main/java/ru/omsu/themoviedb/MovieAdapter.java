package ru.omsu.themoviedb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Result> resultList = new ArrayList<Result>();
    private String api_key = "f0fc35f5ec87f52edeb0d917655e056f";
    private int page = 1;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    private int category = 0;


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        //Log.e("Position", Integer.toString(position));
        if ((resultList.size() - position) <= 5) {
            getMovies();
        }
        holder.bind(resultList.get(position));
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_movie_min, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    private void resetPage(){
        this.page = 1;
    }

    public void clearAndSetPopular(){
        clearItems();
        category = 0;
        resetPage();
        getMovies();

    }
    
    public void clearAndSetUpcoming(){
        clearItems();
        category = 2;
        resetPage();
        getMovies();
    }
    
    public void clearAndSetTopRated(){
        clearItems();
        category = 1;
        resetPage();
        getMovies();
    }

    public void setItems(Collection<Result> results) {
        resultList.addAll(results);
        notifyDataSetChanged();
    }

    public void clearItems() {
        resultList.clear();
        notifyDataSetChanged();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView posterView;
        private ImageView backgroundView;
        private TextView titleView;
        private TextView releaseDateView;
        private TextView overviewView;
        private TextView ratingView;

        public void bind(Result result){
            titleView.setText(result.getTitle());
            overviewView.setText(result.getOverview());
            if (result.getVoteAverage() != 0)
            ratingView.setText(Double.toString(result.getVoteAverage()));
            else ratingView.setText("-");
            releaseDateView.setText(result.getReleaseDate());
            if (!Objects.equals(result.getPosterPath(), null))
                Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/original" + result.getPosterPath()).fitCenter().into(posterView);
            if (!Objects.equals(result.getBackdropPath(), null))
               Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/original" + result.getBackdropPath()).fitCenter().into(backgroundView);
        }



        public MovieViewHolder(View itemView){
            super(itemView);
            posterView = itemView.findViewById(R.id.poster);
            backgroundView = itemView.findViewById(R.id.background);
            titleView = itemView.findViewById(R.id.title);
            releaseDateView = itemView.findViewById(R.id.releaseDate);
            overviewView = itemView.findViewById(R.id.overview);
            ratingView = itemView.findViewById(R.id.rating);
        }
    }

    public void getMovies(){
        switch (category) {
            case 0:
                TMDBService
                    .getInstance()
                    .getJSONApi()
                    .getPopularMovies(api_key, Locale.getDefault().toString().replace("_", "-"), page, Locale.getDefault().getCountry())
                    .enqueue(new Callback<ResultsFromTMDB>() {
                        @Override
                        public void onResponse(Call<ResultsFromTMDB> call, retrofit2.Response<ResultsFromTMDB> response) {
                            ResultsFromTMDB resultsFromTMDB = response.body();
                            page++;
                            resultList.addAll(resultsFromTMDB.results);
                            notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<ResultsFromTMDB> call, Throwable t) {
                        }
                    });
                break;
            case 1:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getTopRatedMovies(api_key, Locale.getDefault().toString().replace("_", "-"), page, Locale.getDefault().getCountry())
                        .enqueue(new Callback<ResultsFromTMDB>() {
                            @Override
                            public void onResponse(Call<ResultsFromTMDB> call, retrofit2.Response<ResultsFromTMDB> response) {
                                ResultsFromTMDB resultsFromTMDB = response.body();
                                page++;
                                resultList.addAll(resultsFromTMDB.results);
                                notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<ResultsFromTMDB> call, Throwable t) {
                            }
                        });
                break;
            case 2:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getUpcomingMovies(api_key, Locale.getDefault().toString().replace("_", "-"), page, Locale.getDefault().getCountry())
                        .enqueue(new Callback<ResultsFromTMDB>() {
                            @Override
                            public void onResponse(Call<ResultsFromTMDB> call, retrofit2.Response<ResultsFromTMDB> response) {
                                ResultsFromTMDB resultsFromTMDB = response.body();
                                page++;
                                resultList.addAll(resultsFromTMDB.results);
                                notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<ResultsFromTMDB> call, Throwable t) {
                            }
                        });
                break;
        }


    }
}
