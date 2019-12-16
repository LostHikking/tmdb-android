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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import ru.omsu.themoviedb.Metadata.TMDB.Item.TVShow.TVShow;
import ru.omsu.themoviedb.Metadata.TMDB.Item.TVShow.TVShowsPage;
import ru.omsu.themoviedb.Metadata.TMDB.TMDBService;
import ru.omsu.themoviedb.R;
import ru.omsu.themoviedb.UI.Activities.ItemInfoActivity;

import static ru.omsu.themoviedb.Settings.API_KEY;
import static ru.omsu.themoviedb.Settings.POPULAR;
import static ru.omsu.themoviedb.Settings.TOP_RATED;
import static ru.omsu.themoviedb.Settings.UPCOMING;
import static ru.omsu.themoviedb.Settings.URL_TMDB_COMPRESSED_IMAGE;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder> {

    private int page = 1;
    private int category = POPULAR;
    private Context context;

    private List<TVShow> tvShowList = Collections.synchronizedList(new ArrayList<TVShow>());


    public TVShowsAdapter(Context context) {
        this.context = context;
    }

    public int getCategory() {
        return category;
    }

    private void setCategory(int category) {
        this.category = category;
    }

    @Override
    public void onBindViewHolder(@NotNull TVShowViewHolder holder, int position) {
        if ((tvShowList.size() - position) <= 5) {
            getMovies();
        }
        holder.bind(tvShowList.get(position));
    }

    @NotNull
    @Override
    public TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    private void resetPage() {
        this.page = 1;
    }

    public void clearAndSetPopular() {
        clearItems();
        setCategory(POPULAR);
        resetPage();
        getMovies();

    }

    public void clearAndSetUpcoming() {
        clearItems();
        setCategory(UPCOMING);
        resetPage();
        getMovies();
    }

    public void clearAndSetTopRated() {
        clearItems();
        setCategory(TOP_RATED);
        resetPage();
        getMovies();
    }

    public void setItems(Collection<TVShow> tvShows) {
        tvShowList.addAll(tvShows);
        notifyDataSetChanged();
    }

    private void clearItems() {
        tvShowList.clear();
        notifyDataSetChanged();
    }

    public void getMovies() {
        switch (category) {
            case POPULAR:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getPopularTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
                        .enqueue(new Callback<TVShowsPage>() {
                            @Override
                            public void onResponse(@NotNull Call<TVShowsPage> call, @NotNull retrofit2.Response<TVShowsPage> response) {
                                TVShowsPage tvShowsPage = response.body();
                                page++;
                                assert tvShowsPage != null;
                                tvShowList.addAll(tvShowsPage.getResults());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(@NotNull Call<TVShowsPage> call, @NotNull Throwable t) {
                            }
                        });
                break;
            case TOP_RATED:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getTopRatedTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
                        .enqueue(new Callback<TVShowsPage>() {
                            @Override
                            public void onResponse(@NotNull Call<TVShowsPage> call, @NotNull retrofit2.Response<TVShowsPage> response) {
                                TVShowsPage tvShowsPage = response.body();
                                page++;
                                assert tvShowsPage != null;
                                tvShowList.addAll(tvShowsPage.getResults());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(@NotNull Call<TVShowsPage> call, @NotNull Throwable t) {
                            }
                        });
                break;
            case UPCOMING:
                TMDBService
                        .getInstance()
                        .getJSONApi()
                        .getLatestTV(API_KEY, Locale.getDefault().toString().replace("_", "-"), page)
                        .enqueue(new Callback<TVShowsPage>() {
                            @Override
                            public void onResponse(@NotNull Call<TVShowsPage> call, @NotNull retrofit2.Response<TVShowsPage> response) {
                                TVShowsPage tvShowsPage = response.body();
                                page++;
                                assert tvShowsPage != null;
                                tvShowList.addAll(tvShowsPage.getResults());
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(@NotNull Call<TVShowsPage> call, @NotNull Throwable t) {
                            }
                        });
                break;
        }


    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterView;
        private ImageView backgroundView;
        private TextView titleView;
        private TextView releaseDateView;
        private TextView overviewView;
        private TextView ratingView;

        TVShowViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.poster);
            backgroundView = itemView.findViewById(R.id.background);
            titleView = itemView.findViewById(R.id.title);
            releaseDateView = itemView.findViewById(R.id.releaseDate);
            overviewView = itemView.findViewById(R.id.overview);
            ratingView = itemView.findViewById(R.id.rating);
        }

        void bind(final TVShow tvShow) {
            titleView.setText(tvShow.getName());
            overviewView.setText(tvShow.getOverview());
            if (tvShow.getVoteAverage() != 0)
                ratingView.setText(Double.toString(tvShow.getVoteAverage()));
            else ratingView.setText("-");
            releaseDateView.setText(tvShow.getFirstAirDate());
            if (!Objects.equals(tvShow.getPosterPath(), null))
                Glide.with(itemView.getContext()).load(URL_TMDB_COMPRESSED_IMAGE + tvShow.getPosterPath()).fitCenter().into(posterView);
            if (!Objects.equals(tvShow.getBackdropPath(), null))
                Glide.with(itemView.getContext()).load(URL_TMDB_COMPRESSED_IMAGE + tvShow.getBackdropPath()).fitCenter().into(backgroundView);
            posterView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemInfoActivity.class);
                    intent.putExtra("TVSHOW_ID", tvShow.getId());
                    context.startActivity(intent);
                }
            });
            backgroundView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ItemInfoActivity.class);
                    intent.putExtra("TVSHOW_ID", tvShow.getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
