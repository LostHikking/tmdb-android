package ru.omsu.themoviedb.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.Movie;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.MoviesPage;
import ru.omsu.themoviedb.Metadata.TMDB.TMDBService;
import ru.omsu.themoviedb.R;
import ru.omsu.themoviedb.UI.Activities.ItemInfoActivity;

import static ru.omsu.themoviedb.Settings.API_KEY;
import static ru.omsu.themoviedb.Settings.POPULAR;
import static ru.omsu.themoviedb.Settings.TOP_RATED;
import static ru.omsu.themoviedb.Settings.UPCOMING;
import static ru.omsu.themoviedb.Settings.URL_TMDB_COMPRESSED_IMAGE;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int page = 1;
    private int category = POPULAR;
    private Context context;

    private List<Movie> movieList = Collections.synchronizedList(new ArrayList<Movie>());


    public MovieAdapter(Context context) {
        this.context = context;
    }

    public int getCategory() {
        return category;
    }

    private void setCategory(int category) {
        this.category = category;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if ((movieList.size() - position) <= 10) {
            getMovies();
        }
        holder.bind(movieList.get(position));
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    private void resetPage(){
        this.page = 1;
    }

    public void clearAndSetPopular(){
        clearItems();
        setCategory(POPULAR);
        resetPage();
        getMovies();

    }
    
    public void clearAndSetUpcoming(){
        clearItems();
        setCategory(UPCOMING);
        resetPage();
        getMovies();
    }
    
    public void clearAndSetTopRated(){
        clearItems();
        setCategory(TOP_RATED);
        resetPage();
        getMovies();
    }

    public void setItems(Collection<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    private void clearItems() {
        movieList.clear();
        notifyDataSetChanged();
    }

    public void getMovies(){
        switch (category) {
            case POPULAR:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getPopularMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, Locale.getDefault().getCountry())
                        .enqueue(new Callback<MoviesPage>() {
                            @Override
                            public void onResponse(Call<MoviesPage> call, retrofit2.Response<MoviesPage> response) {
                                MoviesPage moviesPage = response.body();
                                page++;
                                movieList.addAll(moviesPage.results);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MoviesPage> call, Throwable t) {
                            }
                        });
                break;
            case TOP_RATED:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getTopRatedMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, Locale.getDefault().getCountry())
                        .enqueue(new Callback<MoviesPage>() {
                            @Override
                            public void onResponse(Call<MoviesPage> call, retrofit2.Response<MoviesPage> response) {
                                MoviesPage moviesPage = response.body();
                                page++;
                                movieList.addAll(moviesPage.results);
                                notifyDataSetChanged();
                            }
                            @Override
                            public void onFailure(Call<MoviesPage> call, Throwable t) {
                            }
                        });
                break;
            case UPCOMING:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getUpcomingMovies(API_KEY, Locale.getDefault().toString().replace("_", "-"), page, Locale.getDefault().getCountry())
                        .enqueue(new Callback<MoviesPage>() {
                            @Override
                            public void onResponse(Call<MoviesPage> call, retrofit2.Response<MoviesPage> response) {
                                MoviesPage moviesPage = response.body();
                                page++;
                                movieList.addAll(moviesPage.results);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<MoviesPage> call, Throwable t) {
                            }
                        });
                break;
        }


    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterView;
        private ImageView backgroundView;
        private TextView titleView;
        private TextView releaseDateView;
        private TextView overviewView;
        private TextView ratingView;

        MovieViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.poster);
            backgroundView = itemView.findViewById(R.id.background);
            titleView = itemView.findViewById(R.id.title);
            releaseDateView = itemView.findViewById(R.id.releaseDate);
            overviewView = itemView.findViewById(R.id.overview);
            ratingView = itemView.findViewById(R.id.rating);
        }

        void bind(final Movie movie) {
            titleView.setText(movie.getTitle());
            overviewView.setText(movie.getOverview());
            if (movie.getVoteAverage() != 0)
                ratingView.setText(movie.getVoteAverage().toString());
            else ratingView.setText("-");
            releaseDateView.setText(movie.getReleaseDate());
            if (!Objects.equals(movie.getPosterPath(), null))
                Glide.with(itemView.getContext()).load(URL_TMDB_COMPRESSED_IMAGE + movie.getPosterPath()).fitCenter().dontTransform().into(posterView);
            if (!Objects.equals(movie.getBackdropPath(), null))
                Glide.with(itemView.getContext()).load(URL_TMDB_COMPRESSED_IMAGE + movie.getBackdropPath()).fitCenter().dontTransform().into(backgroundView);
            posterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemInfoActivity.class);
                    intent.putExtra("MOVIE_ID", movie.getId());
                    context.startActivity(intent);
                }
            });
            backgroundView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemInfoActivity.class);
                    intent.putExtra("MOVIE_ID", movie.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
