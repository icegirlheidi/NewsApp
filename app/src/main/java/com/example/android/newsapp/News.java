package com.example.android.newsapp;


public class News {

    private String mTitle;
    //private String mDate;
    private String mSection;
    //private String mUrl;

    /*public News(String title, String date, String section, String url) {
        mTitle = title;
        mDate = date;
        mSection = section;
        mUrl = url;
    }*/
    public News(String title, String section) {
        mTitle = title;
        mSection = section;
    }

    /*public String getDate() {
        return mDate;
    }*/

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    /*public String getUrl() {
        return mUrl;
    }*/
}
