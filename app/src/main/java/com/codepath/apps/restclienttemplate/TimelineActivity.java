package com.codepath.apps.restclienttemplate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {
    public static final String TAG = "TimeLineActivity";
    private final int REQUEST_CODE = 20;
    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;
    Button logoutButton;
    MenuItem miActionProgressItem;
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // we ensure that background operations of the parent class are done
        setContentView(R.layout.activity_timeline); // we inflate the timeline view
        client = TwitterApp.getRestClient(this); // we get the twitter client reference
        tweets = new ArrayList<>();

        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                        ACTION BAR SECTION
        ------------------------------------------------------------------------------------------------------------------------------------*/
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.compose_icon);
        actionBar.setDisplayUseLogoEnabled(true);

        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                        SWIPE CONTAINER SECTION
        ------------------------------------------------------------------------------------------------------------------------------------*/
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer); // We get a reference to the "swipe container" view
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /* --------------------------------------------
            // we setup a refresh listener which through fetchTimelineAsync makes a request for new data
            and if the request succeeds we clear actual tweets (from list and rv) and append new ones
             -------------------------------------------- */
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);


        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                        RECYCLED VIEW SECTION
        ------------------------------------------------------------------------------------------------------------------------------------*/
        rvTweets = findViewById(R.id.rvTweets); // we generate a reference to  the recycler view
        adapter = new TweetsAdapter(this, tweets); // we make an instance of Tweets adapter based on the tweets list
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager); // we bind a layout manager to RV
        rvTweets.setAdapter(adapter); // we bind the adapter to the RV
        populateHomeTimeline(); // we fill the RV

        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                 BINDING INFINITE SCROLLING TO RV
        ------------------------------------------------------------------------------------------------------------------------------------*/
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //loadNextDataFromApi(page);
                Toast.makeText(TimelineActivity.this, "fetching new data", Toast.LENGTH_LONG);
                Log.e("endlessScrolling", "fetching new data");
                fetchNewData();
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTweets.addOnScrollListener(scrollListener);
    }

    private void populateHomeTimeline() {
        // client.getHomeTimeline specifies the statuses request details
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                    //hideProgressBar();
                } catch (JSONException e) {
                    Log.e(TAG, "Json exception", e);
                    //e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure" + response, throwable);
            }
        });
    }

    private void fetchNewData(){
        client.addItemsToTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                    //hideProgressBar();
                } catch (JSONException e) {
                    Log.e(TAG, "Json exception", e);
                    //e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure" + response, throwable);
            }
        },
        tweets.get(tweets.size()-1).id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // we inflate our menu layout
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        return true;
    }

    /*@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);

        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // we check if the item seleted is the compose item (pencil drawing)
        if (item.getItemId() == R.id.compose) {
            //compose item has been selected
            //Toast.makeText(this, "compose", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ComposeActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
            return true;
        }else if (item.getItemId() == R.id.iclogoutButton){
            onLogoutButton();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.e("activityResult", String.valueOf(requestCode));
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            // GET DATA FROM INTENT -> TWEET
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //UPDATE THE RV WITH TWEET
            tweets.add(0, tweet);
            //modify data source and then update adapter
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
        }else if(requestCode == 30 && resultCode == RESULT_OK){
            Toast.makeText(this, "reply handled in timeline", Toast.LENGTH_LONG);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onLogoutButton(){
        client.clearAccessToken();
        finish();
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                /*adapter.clear();
                System.out.println(tweets);*/
                tweets.clear();
                adapter.notifyDataSetChanged();
                //tweets.clear();
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // ...the data has come back, add new items to your adapter...
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("DEBUG", "Fetch timeline error: ");
            }
        });
    }

    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
}