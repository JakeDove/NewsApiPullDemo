package com.appivate.newsapplication;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

public class NewsTaskLoader extends AsyncTaskLoader<List<NewsModel>> {
    String queryURL;


    public NewsTaskLoader(@NonNull Context context, String urlToBeLoaded) {
        super(context);
        queryURL = urlToBeLoaded;
    }

    @Override
    public List<NewsModel> loadInBackground() {
        if(queryURL== null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<NewsModel> newsData;
        newsData = null;
        try {
            newsData = HttpHelperClass.getNewsData(queryURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsData;
};
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
