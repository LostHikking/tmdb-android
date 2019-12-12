package ru.omsu.themoviedb.Activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import ru.omsu.themoviedb.R;

public class MovieActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ImageView posterView = findViewById(R.id.poster);
        Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/original/498AdCguRoD1mpizIeKi8nDTOQO.jpg")
                .fitCenter()
                .into(posterView);
        ImageView backgroundView = findViewById(R.id.background);
        Glide.with(this).load("https://image.tmdb.org/t/p/original/498AdCguRoD1mpizIeKi8nDTOQO.jpg")
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(40, 10)))
                .into(backgroundView);
    }
}