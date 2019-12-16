package ru.omsu.themoviedb.UI.Activities;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.Collection;
import ru.omsu.themoviedb.Metadata.TMDB.Item.Movie.Movie;
import ru.omsu.themoviedb.Metadata.TMDB.MovieCredits;
import ru.omsu.themoviedb.Metadata.TMDB.TMDBService;
import ru.omsu.themoviedb.Metadata.TMDB.Videos;
import ru.omsu.themoviedb.R;

import static ru.omsu.themoviedb.Settings.API_KEY;
import static ru.omsu.themoviedb.Settings.PATH_IMAGE_PERSON_NO_PHOTO;
import static ru.omsu.themoviedb.Settings.URL_TMDB_COMPRESSED_IMAGE;
import static ru.omsu.themoviedb.Settings.URL_TMDB_ORIGINAL_IMAGE;
import static ru.omsu.themoviedb.Settings.URL_YOUTUBE_VIDEO;

public class ItemInfoActivity extends AppCompatActivity {
    @BindView(R.id.poster)
    ImageView posterView;
    @BindView(R.id.title)
    TextView titleView;
    @BindView(R.id.overview)
    TextView overviewView;
    @BindView(R.id.rating)
    TextView ratingView;
    @BindView(R.id.info_line)
    TextView infoLineView;
    @BindView(R.id.movie_layout)
    RelativeLayout relativeLayout;
    @BindView(R.id.play_trailer_button)
    Button playTrailerButton;
    @BindView(R.id.cast)
    LinearLayout castLinearLayout;
    @BindView(R.id.crew)
    LinearLayout crewLinearLayout;
    @BindView(R.id.collection)
    LinearLayout collectionLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        int movie_id = getIntent().getIntExtra("MOVIE_ID", 0);
        TMDBService.getInstance()
                .getJSONApi()
                .getMovieDetails(movie_id, API_KEY, Locale.getDefault().toString().replace("_", "-"))
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(@NotNull Call<Movie> call, @NotNull Response<Movie> response) {
                        Movie movie = response.body();
                        assert movie != null;
                        if (movie.getCollection() != null)
                            TMDBService
                                    .getInstance()
                                    .getJSONApi()
                                    .getCollectionDetails(movie.getCollection().getId(), API_KEY, Locale.getDefault().toString().replace("_", "-"))
                                    .enqueue(new Callback<Collection>() {
                                        @Override
                                        public void onResponse(@NotNull Call<Collection> call, @NotNull Response<Collection> response) {
                                            Collection collection = response.body();
                                            assert collection != null;
                                            for (int i = 0; i < collection.getParts().size(); i++) {
                                                View collectionView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_item_small, null);
                                                TextView titleView = collectionView.findViewById(R.id.title);
                                                TextView releaseDateView = collectionView.findViewById(R.id.release_date);
                                                ImageView posterView = collectionView.findViewById(R.id.poster);
                                                titleView.setText(collection.getParts().get(i).getTitle());
                                                releaseDateView.setText(collection.getParts().get(i).getReleaseDate());
                                                Glide.with(getApplicationContext()).load(URL_TMDB_COMPRESSED_IMAGE + collection.getParts().get(i).getPosterPath())
                                                        .fitCenter()
                                                        .into(posterView);
                                                collectionLinearLayout.addView(collectionView);

                                            }
                                        }

                                        @Override
                                        public void onFailure(@NotNull Call<Collection> call, @NotNull Throwable t) {

                                        }
                                    });
                        overviewView.setText(movie.getOverview());
                        titleView.setText(movie.getTitle());
                        infoLineView.setText(movie.getGenres().get(0).getName() + " | " + movie.getProductionCountries().get(0).getIso31661() + " | " + movie.getReleaseDate());
                        ratingView.setText(Html.fromHtml("<b>" + movie.getVoteAverage() + "</b>/10"));
                        Glide.with(getApplicationContext()).load(URL_TMDB_ORIGINAL_IMAGE + movie.getPosterPath())
                                .fitCenter()
                                .into(posterView);
                        Glide.with(getApplicationContext()).load(URL_TMDB_ORIGINAL_IMAGE + movie.getPosterPath())
                                .apply(RequestOptions.bitmapTransform(new BlurTransformation(40, 10)))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        resource.setAlpha(70);
                                        relativeLayout.setBackground(resource);
                                    }
                                });

                    }

                    @Override
                    public void onFailure(@NotNull Call<Movie> call, @NotNull Throwable t) {

                    }
                });
        TMDBService.getInstance()
                .getJSONApi()
                .getVideos(movie_id, API_KEY, Locale.getDefault().toString().replace("_", "-"))
                .enqueue(new Callback<Videos>() {
                    @Override
                    public void onResponse(@NotNull Call<Videos> call, @NotNull Response<Videos> response) {
                        Videos videos = response.body();
                        assert videos != null;
                        if (videos.getResults() == null)
                            playTrailerButton.setVisibility(View.GONE);
                        else {
                            if (videos.getResults().get(0).getSite().equals("YouTube")) {
                                final List<Videos.Video> list = videos.getResults();
                                playTrailerButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + list.get(0).getKey()));
                                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse(URL_YOUTUBE_VIDEO + list.get(0).getKey()));
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
                    public void onFailure(@NotNull Call<Videos> call, @NotNull Throwable t) {
                    }
                });

        TMDBService.getInstance()
                .getJSONApi()
                .getCredits(movie_id, API_KEY)
                .enqueue(new Callback<MovieCredits>() {
                    @Override
                    public void onResponse(@NotNull Call<MovieCredits> call, @NotNull Response<MovieCredits> response) {
                        MovieCredits movieCredits = response.body();
                        assert movieCredits != null;
                        for (int i = 0; ((i < movieCredits.getCast().size()) && (i < 30)); i++) {
                            View castView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_person, null);
                            TextView nameView = castView.findViewById(R.id.name);
                            TextView roleView = castView.findViewById(R.id.role);
                            ImageView charView = castView.findViewById(R.id.person_image);
                            nameView.setText(movieCredits.getCast().get(i).getName());
                            roleView.setText(movieCredits.getCast().get(i).getCharacter());
                            if (movieCredits.getCast().get(i).getProfilePath() != null)
                                Glide.with(getApplicationContext()).load(URL_TMDB_COMPRESSED_IMAGE + movieCredits.getCast().get(i).getProfilePath())
                                        .fitCenter()
                                        .into(charView);
                            else
                                Glide.with(getApplicationContext()).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(charView);
                            castLinearLayout.addView(castView);
                        }
                        for (int i = 0; ((i < movieCredits.getCrew().size()) && (i < 30)); i++) {
                            View castView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_person, null);
                            TextView nameView = castView.findViewById(R.id.name);
                            TextView roleView = castView.findViewById(R.id.role);
                            ImageView charView = castView.findViewById(R.id.person_image);
                            nameView.setText(movieCredits.getCrew().get(i).getName());
                            roleView.setText(movieCredits.getCrew().get(i).getJob());
                            if (movieCredits.getCrew().get(i).getProfilePath() != null)
                                Glide.with(getApplicationContext()).load(URL_TMDB_COMPRESSED_IMAGE + movieCredits.getCrew().get(i).getProfilePath())
                                        .fitCenter()
                                        .into(charView);
                            else
                                Glide.with(getApplicationContext()).asBitmap().load(Uri.parse(PATH_IMAGE_PERSON_NO_PHOTO)).fitCenter().into(charView);
                            crewLinearLayout.addView(castView);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MovieCredits> call, @NotNull Throwable t) {
                    }
                });
    }
}