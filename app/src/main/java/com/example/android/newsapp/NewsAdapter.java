package com.example.android.newsapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {


    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    static class ViewHolderItem {
        TextView textViewItemTitle;
        TextView textViewItemSection;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolder;

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.textViewItemTitle = (TextView) listItemView.findViewById(R.id.title);
            viewHolder.textViewItemSection = (TextView) listItemView.findViewById(R.id.section);
            listItemView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) listItemView.getTag();
        }

        News currentNews = getItem(position);

        String title = currentNews.getTitle();
        String section = currentNews.getSection();

        viewHolder.textViewItemTitle.setText(title);
        viewHolder.textViewItemSection.setText(section);

        return listItemView;
    }
}
