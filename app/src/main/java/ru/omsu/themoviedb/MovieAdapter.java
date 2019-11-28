package ru.omsu.themoviedb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Result> resultList = new ArrayList<>();

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
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
            ratingView.setText(Double.toString(result.getVoteAverage()));
            releaseDateView.setText(result.getReleaseDate());
            if (!Objects.equals(result.getPosterPath(), null))
                Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/original" + result.getPosterPath()).override(250, 500).fitCenter().into(posterView);
            if (!Objects.equals(result.getBackdropPath(), null))
                Glide.with(itemView.getContext()).load("https://image.tmdb.org/t/p/original" + result.getBackdropPath()).override(960, 540).fitCenter().into(backgroundView);

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
}
