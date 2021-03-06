package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.newsapp.R.id.recycler_view;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getName();
    private NewsAdapter mAdapter;
    private static final int LOADER_ID = 1;
    private static final String REQUEST_URL = "http://content.guardianapis.com/search?q=Steph%20AND%20Curry&api-key=test";
    private RecyclerView mRecyclerView;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        mRecyclerView.setAdapter(mAdapter);

//      //Use the ConnectivityManager to query the active network
        // and determine if it has Internet connectivity.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            // Initialize the loader if there is internet connection
            getLoaderManager().initLoader(LOADER_ID, null, this);
        } else {
            // When there is no internet connection,
            // Remove the loading progress bar and display no internet connection
            View loadingProgress = findViewById(R.id.loading_progress);
            loadingProgress.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            mEmptyTextView.setText(R.string.no_intenet);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            // Open SettingsActivity if settings is clicked
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );
        String pageSize = sharedPreferences.getString(
                getString(R.string.settings_page_size_key),
                getString(R.string.settings_page_size_default)
        );
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("section", "sport");
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("page-size", pageSize);
        Log.e(LOG_TAG, uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsItems) {
        // Clear the previous adapter by creating a new adapter
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        if (newsItems != null && !newsItems.isEmpty()) {
            mAdapter = new NewsAdapter(this, newsItems);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mEmptyTextView.setText(R.string.no_news);
        }
        View loadingProgress = findViewById(R.id.loading_progress);
        // Remove loading progress bar after making http request and updating ui
        loadingProgress.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
    }

}
