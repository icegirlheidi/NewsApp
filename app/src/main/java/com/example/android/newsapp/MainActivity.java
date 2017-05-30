package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView newsListView = (ListView)findViewById(R.id.list);

        mAdapter = new NewsAdapter (MainActivity.this, new ArrayList<News>());
        newsListView.setAdapter(mAdapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }
}
