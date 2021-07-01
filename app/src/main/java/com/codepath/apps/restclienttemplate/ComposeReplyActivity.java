package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeReplyActivity extends ComposeActivity {
    Tweet tweet;
    String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                    WE INFLATE THE LAYOUT AND REFERENCE VIEWS INSIDE IT
        ------------------------------------------------------------------------------------------------------------------------------------*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        btnTweet.setText("Reply");

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        author = tweet.user.screenName;
        //System.out.println(author.screenName);

        /* ------------------------------------------------------------------------------------------------------------------------------------
                                                        WE ADD ONCLICK LISTENER TO BUTTON
        ------------------------------------------------------------------------------------------------------------------------------------*/
        // add click listener to button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //--------------------------------------------- WE CHECK THE ET VIEW AND CHECK CONSTRAINTS ----------------------------------------------------
                String tweetContent = etCompose.getText().toString();
                if (tweetContent.isEmpty()){
                    Toast.makeText(ComposeReplyActivity.this, "sorry the Tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeReplyActivity.this, "sorry the Tweet is too long", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*---------------------------------------------------------------------------------------------------------------------------------------------
                IF TWEET CONTENT IS OK WE MAKE A REQUEST TO REPLY AND IF REQUEST IS SUCCESSFUL WE GO BACK TO TIMELINE
                --------------------------------------------------------------------------------------------------------------------------------------------- */
                String replyContent = etCompose.getText().toString();
                client.replyToTweet(replyContent + " \n\nin Reply to: @" + author , tweet.id, "", new JsonHttpResponseHandler() {
                    //@JoseAngelDelAn4
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JsonHttpResponseHandler.JSON json) {
                        Toast toast = Toast.makeText(ComposeReplyActivity.this, "tweet replied", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e("replyingTweet", "error: " + response);
                    }
                });


            }
        });
    }


}