package ru.omsu.themoviedb.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.util.FixedPreloadSizeProvider;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.omsu.themoviedb.MovieAdapter;
import ru.omsu.themoviedb.R;
import ru.omsu.themoviedb.Result;
import ru.omsu.themoviedb.ResultsFromTMDB;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    ResultsFromTMDB resultsFromTMDB;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Result> loadedResult;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadedResult = null;
        FixedPreloadSizeProvider fixedPreloadSizeProviderForPoster = new FixedPreloadSizeProvider(250,500);
        FixedPreloadSizeProvider fixedPreloadSizeProviderForBackground = new FixedPreloadSizeProvider(960,540);

        new AsyncRequest().execute();
        initRecyclerView();

    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.list);
        //final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        new AsyncRequest().execute();
        NestedScrollView nestedScrollView = findViewById(R.id.listView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                Log.e("ProductFragment", "position button " + " scrollY " + scrollY);
                if (scrollY > oldScrollY) {
                    Log.e("ProductFragment", "down");
                } else {
                    Log.e("ProductFragment", "up");
                    new AsyncRequest().execute();
                }
            }
        });
    }

    class AsyncRequest extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... arg) {
            try {
                i=i+1;
                return run("https://api.themoviedb.org/3/movie/popular?api_key=f0fc35f5ec87f52edeb0d917655e056f&language=ru-RU&page=" + i);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            resultsFromTMDB = gson.fromJson(s, ResultsFromTMDB.class);
            movieAdapter.setItems(resultsFromTMDB.results);
        }
    }
        String run (String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}

