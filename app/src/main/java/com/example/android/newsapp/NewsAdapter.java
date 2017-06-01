package com.example.android.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String LOG_TAG = NewsAdapter.class.getName();
    public List<News> mNewsList;
    private Context mContext;

    // The webPublicationDate from json file is like "2017-04-25T14:28:04Z"
    // so "T" separates date and time
    private static final String DATE_SEPARATOR = "T";


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView sectionTextView;
        public TextView dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            sectionTextView = (TextView) itemView.findViewById(R.id.section);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
        }
    }

    public NewsAdapter(Context context, List<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View newsView = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(newsView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int position) {
        final News news = mNewsList.get(position);

        final TextView titleTextView = viewHolder.titleTextView;
        titleTextView.setText(news.getTitle());

        // Show details of news when news title is clicked
        titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri newsUrl = Uri.parse(news.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUrl);
                mContext.startActivity(intent);
            }
        });

        TextView sectionTextView = viewHolder.sectionTextView;
        sectionTextView.setText(news.getSection());

        TextView dateTextView = viewHolder.dateTextView;
        String[] dateParts = news.getDate().split(DATE_SEPARATOR);
        String date = dateParts[0];
        String time = dateParts[1];

        // Show only date (i.e. "2017-04-25")
        dateTextView.setText(date);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
