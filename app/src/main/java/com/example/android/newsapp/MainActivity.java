package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.newsapp.R.id.recycler_view;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private static final String REQUEST_URL = "http://content.guardianapis.com/search?q=curry&order-by=newest&section=sport&api-key=test";
    private RecyclerView mRecyclerView;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEmptyTextView = (TextView)findViewById(R.id.empty_text_view);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        mRecyclerView.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            getLoaderManager().initLoader(LOADER_ID, null,this);
        } else {
            View loadingProgress = findViewById(R.id.loading_progress);
            loadingProgress.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(R.string.no_intenet);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsItems) {
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        if(newsItems != null && !newsItems.isEmpty()) {
            mAdapter = new NewsAdapter(this, newsItems);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mEmptyTextView.setText(R.string.no_news);
        }

        View loadingProgress = findViewById(R.id.loading_progress);
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
    }
}
