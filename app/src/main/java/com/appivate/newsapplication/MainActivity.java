package com.appivate.newsapplication;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appivate.newsapplication.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsModel>> {

    private NewsAdapter newsAdapter;
    ArrayList<NewsModel> fakeNewsArray;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fakeNewsArray = new ArrayList<>();
        showLoadingScreen();
        checkConnectionandAttemptLoad();
        setSwipeToRefresh();
    }

    public void checkConnectionandAttemptLoad() {
        // check for internet connection
        if (HttpHelperClass.isConnected(this)) {
            Log.d("info", "starting the loader");
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(1, null, this);
        } else {
            showUnableToFetchDataView("No network connection available");
        }
    }

    public void showLoadingScreen() {
        binding.constraintLayout.setAlpha(0.5f);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    ;

    public void hideLoadingScreen() {
        binding.constraintLayout.setAlpha(1f);
        binding.progressBar.setVisibility(View.INVISIBLE);
    }


    public void showUnableToFetchDataView(String errorReason) {
        binding.newsRecyclerView.setVisibility(View.INVISIBLE);
        binding.errorText.setVisibility(View.VISIBLE);
        binding.errorText.setText(errorReason);
    }

    public void hideUnableToFetchDataView() {
        binding.newsRecyclerView.setVisibility(View.VISIBLE);
        binding.errorText.setVisibility(View.INVISIBLE);

    }

    public void setSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            binding.swipeRefreshLayout.setRefreshing(true);
            checkConnectionandAttemptLoad();
        });
    }

    @NonNull
    @Override
    public Loader<List<NewsModel>> onCreateLoader(int id, Bundle args) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("api-key", "test")
                .appendQueryParameter("show-fields", "thumbnail,trailText")
                .appendQueryParameter("show-tags", "contributor");

        return new NewsTaskLoader(this, builder.build().toString());
    }


    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsModel>> loader, List<NewsModel> data) {
          if (data.size() == 0) {
        showUnableToFetchDataView("There was a problem fetching your news");
    } else {
        hideUnableToFetchDataView();
        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager newsLayoutManager = new LinearLayoutManager(this);
        newsAdapter = new NewsAdapter(fakeNewsArray);
        newsRecyclerView.setLayoutManager(newsLayoutManager);
        newsRecyclerView.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openWebViewOnItem(fakeNewsArray.get(position));
            }
        });
        newsAdapter.updateList(data);
        newsAdapter.notifyDataSetChanged();
        hideLoadingScreen();
        binding.swipeRefreshLayout.setRefreshing(false);
    }

}

    public void openWebViewOnItem(NewsModel newsItem) {
        String URL = newsItem.getNewsURL();
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No web browser installed",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsModel>> loader) {

    }


}