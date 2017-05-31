package com.example.android.newsapp;


public class News {

    private String mTitle;
    private String mSection;
    private String mDate;

    //private String mUrl;

    /*public News(String title, String date, String section, String url) {
        mTitle = title;
        mDate = date;
        mSection = section;
        mUrl = url;
    }*/
    public News(String title, String section, String date) {
        mTitle = title;
        mSection = section;
        mDate = date;
    }


    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    /*public String getUrl() {
        return mUrl;
    }*/
}
