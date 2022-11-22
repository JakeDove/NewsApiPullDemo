package com.appivate.newsapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//builds a news model from a JSON Object
public class NewsModelBuilder {
    JSONObject jsonObject;
    NewsModel newsModel;


    public List<NewsModel> buildNewsModelFromJsonObject(String jSonReponseString) {

            // if the JSON string is empty or null then return early.
            // check for empty JSON String

            if(jSonReponseString.length() == 0) {
                return null;
            }

            List<NewsModel> createdNewsList = new ArrayList<>();

            // Try to parse the JSON response string. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            try {

                String newsTitle;
                String sectionName;
                Date newsDate;
                String newsDateString;
                String newsAuthor;
                String newsText;
                String newsUrl;
                String thumbnail = "";
                String trailText = "";

                JSONObject baseJsonResponse = new JSONObject(jSonReponseString);

                // Extract the JSONObject associated with the key string "response"
                JSONObject responseFromJSONObject = baseJsonResponse.getJSONObject("response");

                // Extract the JSONArray associated with the key string "results"
                JSONArray resultsArray = responseFromJSONObject.getJSONArray("results");


                //iterate through the resultArray and create a newsModelObject for each object

                for (int i = 0; i < resultsArray.length(); i++) {
                    // Get a single newsList at index i
                    JSONObject currentNewsItem = resultsArray.getJSONObject(i);
                    newsTitle = currentNewsItem.getString("webTitle");
                    sectionName = currentNewsItem.getString("sectionName");
                    newsDateString = currentNewsItem.getString("webPublicationDate");
                    newsUrl = currentNewsItem.getString("webUrl");
                    JSONObject thumbnailObj = currentNewsItem.getJSONObject("fields");
                    JSONArray tags = currentNewsItem.getJSONArray("tags");
                    JSONObject Author = tags.getJSONObject(0);
                    newsAuthor = Author.getString("webTitle");

                    // Throw an exception if one or more thumbnail is missing
                    try {
                        thumbnail = thumbnailObj.getString("thumbnail");
                        trailText = thumbnailObj.getString("trailText");
                        Log.d("thumbnail", "thumbail url is " + thumbnail);
                    } catch (JSONException e) {
                        Log.e("JSON", "Couldnt get thumbnail object");
                    }

                    // Create a new {@Link News} object
                    NewsModel news = new NewsModel();
                    news.setNewsTitle(newsTitle);
                    news.setNewsTopic(sectionName);
                    news.setNewsURL(newsUrl);
                    news.setThumbnail(thumbnail);
                    news.setTrailText(trailText);
                    news.setAuthor(newsAuthor);

                    news.setNewsDate(convertStringToDate(newsDateString));
                    createdNewsList.add(news);
                }
            } catch (JSONException e) {
                // catch the exception here, so the app doesn't crash. Print a log message.
                Log.e("JSON", "Problem getting JSON Objects ", e);
            }
            // Return the newsList
            return createdNewsList;
        }


        public Date convertStringToDate(String newsDate)  {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(newsDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }
    }




