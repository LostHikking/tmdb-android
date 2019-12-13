package ru.omsu.themoviedb.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.omsu.themoviedb.R;
import ru.omsu.themoviedb.TMDB.Data.Movie;
import ru.omsu.themoviedb.TMDB.Data.MovieCredits;
import ru.omsu.themoviedb.TMDB.Data.VideoResult;
import ru.omsu.themoviedb.TMDB.TMDBService;

public class MovieActivity extends AppCompatActivity {
    private String api_key = "f0fc35f5ec87f52edeb0d917655e056f";
    private int movie_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movie_id = getIntent().getIntExtra("MOVIE_ID", 0);
        final ImageView posterView = findViewById(R.id.poster);
        final ImageView backgroundView = findViewById(R.id.background);
        final TextView titleView = findViewById(R.id.title);
        final TextView overviewView = findViewById(R.id.overview);
        final TextView ratingView = findViewById(R.id.rating);
        final TextView infoLineView = findViewById(R.id.info_line);
        final RelativeLayout relativeLayout = findViewById(R.id.movie_layout);
        final Button playTrailerButton = findViewById(R.id.play_trailer_button);
        TMDBService.getInstance()
                .getJSONApi()
                .getDetails(movie_id, api_key, Locale.getDefault().toString().replace("_", "-"))
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie movie = response.body();
                        overviewView.setText(movie.getOverview());
                        titleView.setText(movie.getTitle());
                        infoLineView.setText(movie.getGenres().get(0).getName() + " | " + movie.getProductionCountries().get(0).getIso31661() + " | " + movie.getReleaseDate());
                        ratingView.setText(Html.fromHtml("<b>" + movie.getVoteAverage() + "</b>/10"));
                        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/original" + movie.getPosterPath())
                                .fitCenter()
                                .into(posterView);
                        Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/original" + movie.getPosterPath())
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation(40, 10)))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        resource.setAlpha(90);
                                        relativeLayout.setBackground(resource);
                                    }
                                });

                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {

                    }
                });
        TMDBService.getInstance()
                .getJSONApi()
                .getVideos(movie_id, api_key, Locale.getDefault().toString().replace("_", "-"))
                .enqueue(new Callback<VideoResult>() {
                    @Override
                    public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                        VideoResult videoResult = response.body();
                        if (videoResult.getResults() == null)
                            playTrailerButton.setVisibility(View.GONE);
                        else {
                            if (videoResult.getResults().get(0).getSite() == "YouTube") {
                                final List<VideoResult.Result_> list = videoResult.getResults();
                                playTrailerButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + list.get(0).getKey()));
                                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("http://www.youtube.com/watch?v=" + list.get(0).getKey()));
                                        try {
                                            startActivity(appIntent);
                                        } catch (ActivityNotFoundException ex) {
                                            startActivity(webIntent);
                                        }
                                    }
                                });
                            } else
                                playTrailerButton.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoResult> call, Throwable t) {
                    }
                });
        final LinearLayout castLinearLayout = findViewById(R.id.cast);
        final LinearLayout crewLinearLayout = findViewById(R.id.crew);
        TMDBService.getInstance()
                .getJSONApi()
                .getCredits(movie_id, api_key)
                .enqueue(new Callback<MovieCredits>() {
                    @Override
                    public void onResponse(Call<MovieCredits> call, Response<MovieCredits> response) {
                        MovieCredits movieCredits = response.body();
                        for (int i = 0; ((i < movieCredits.getCast().size()) && (i < 30)); i++) {
                            View castView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.person_card, null);
                            TextView nameView = castView.findViewById(R.id.name);
                            TextView roleView = castView.findViewById(R.id.role);
                            ImageView charView = castView.findViewById(R.id.person_image);
                            nameView.setText(movieCredits.getCast().get(i).getName());
                            roleView.setText(movieCredits.getCast().get(i).getCharacter());
                            if (movieCredits.getCast().get(i).getProfilePath() != null)
                                Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + movieCredits.getCast().get(i).getProfilePath())
                                        .fitCenter()
                                        .into(charView);
                            else
                                Glide.with(getApplicationContext()).asBitmap().load(Uri.parse("file:///android_asset/nophoto.jpg")).fitCenter().into(charView);
                            castLinearLayout.addView(castView);
                        }
                        for (int i = 0; ((i < movieCredits.getCrew().size()) && (i < 30)); i++) {
                            View castView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.person_card, null);
                            TextView nameView = castView.findViewById(R.id.name);
                            TextView roleView = castView.findViewById(R.id.role);
                            ImageView charView = castView.findViewById(R.id.person_image);
                            nameView.setText(movieCredits.getCrew().get(i).getName());
                            roleView.setText(movieCredits.getCrew().get(i).getJob());
                            if (movieCredits.getCrew().get(i).getProfilePath() != null)
                                Glide.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500" + movieCredits.getCrew().get(i).getProfilePath())
                                        .fitCenter()
                                        .into(charView);
                            else
                                Glide.with(getApplicationContext()).asBitmap().load(Uri.parse("file:///android_asset/nophoto.jpg")).fitCenter().into(charView);
                            crewLinearLayout.addView(castView);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieCredits> call, Throwable t) {
                    }
                });
    }
}