package com.appivate.newsapplication;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

public class NewsModel {

    private String newsTitle;
    private String newsTrailText;
    private Date newsDate;
    private String newsURL;
    private String newsTopic;
    private String thumbnail;
    private String author;
    private String webURL;

    public NewsModel(String newsTitle, Date newsDate, String newsURL, String newsTopic, String thumbnail, String webURL, String author) {
        this.newsTitle = newsTitle;
        this.newsDate = newsDate;
        this.newsURL = newsURL;
        this.newsTopic = newsTopic;
        this.thumbnail = thumbnail;
        this.webURL = webURL;
        this.author = author;
    }

    public NewsModel() {
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getTrailText() {
        return newsTrailText;
    }

    public void setTrailText(String newsTrailText) {
        this.newsTrailText = newsTrailText;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }

    public String getNewsTopic() {
        return newsTopic;
    }

    public void setNewsTopic(String newsText) {
        this.newsTopic = newsText;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getNewsDateAsString()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd", Locale.ENGLISH);
            return sdf.format(getNewsDate());
        }

}
