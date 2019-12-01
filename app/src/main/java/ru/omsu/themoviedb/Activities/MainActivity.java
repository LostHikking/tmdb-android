package ru.omsu.themoviedb.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.omsu.themoviedb.MovieAdapter;
import ru.omsu.themoviedb.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        initRecyclerView();
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_popular:
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.get(MainActivity.this).clearDiskCache();
                                    }
                                }).start();
                                movieAdapter.clearAndSetPopular();
                                break;
                            case R.id.action_toprated:
                                movieAdapter.clearAndSetTopRated();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.get(MainActivity.this).clearDiskCache();
                                    }
                                }).start();
                                break;
                            case R.id.action_upcoming:
                                movieAdapter.clearAndSetUpcoming();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Glide.get(MainActivity.this).clearDiskCache();
                                    }
                                }).start();
                                break;
                        }
                        return false;
                    }
                });
    }




    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.getMovies();
    }
}

