package com.appivate.newsapplication;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpHelperClass {

    HttpHelperClass() {
    }

    //checks for mobile internet connection
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<NewsModel> getNewsData(String requestUrl) throws IOException {
        URL url = createUrlFromString(requestUrl);
        String responseFromJSON;

        responseFromJSON = generateHTTPRequest(url);

        // Builds a news model from the JSonReponse.
        List<NewsModel> createdNewsList;
        NewsModelBuilder newsModelBuilder = new NewsModelBuilder();
        createdNewsList = newsModelBuilder.buildNewsModelFromJsonObject(responseFromJSON);
        return createdNewsList;
    }

    private static URL createUrlFromString(String stringtoBeConverted) {
        URL url = null;
        try {
            url = new URL(stringtoBeConverted);
        } catch (MalformedURLException e) {
        }
        return url;
    }

    private static String generateHTTPRequest(URL url) throws IOException {
        String responseFromJSON = "";

        // checks for null URL.
        if (url == null) {
            return responseFromJSON;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000); // 15000 millisecond.
            urlConnection.setRequestMethod("GET"); // GET method.
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                responseFromJSON = readStreamResponse(inputStream);
            } else {
                Log.e("URL", "couldnt get URL response, The Error code is = " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("URL", "couldnt get JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return responseFromJSON;
    }

    private static String readStreamResponse(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }


}



