package com.example.android.newsapp;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public List<News> mNewsList;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView sectionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            sectionTextView = (TextView) itemView.findViewById(R.id.section);
        }
    }

    public NewsAdapter (Context context, List<News> newsList) {
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
        News news = mNewsList.get(position);
        TextView titleTextView = viewHolder.titleTextView;
        titleTextView.setText(news.getTitle());
        TextView sectionTextView = viewHolder.sectionTextView;
        sectionTextView.setText(news.getSection());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public void updateItems(List<News> myNews) {
        this.mNewsList = myNews;
        notifyDataSetChanged();
    }
    /*public void clear() {
        int size = mNewsList.size();
        mNewsList.clear();
        notifyItemRangeRemoved(0, size);
    }*/


}
