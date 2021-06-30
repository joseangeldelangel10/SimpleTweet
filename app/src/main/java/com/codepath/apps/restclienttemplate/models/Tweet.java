package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


@Parcel
public class Tweet {
    //private static ArrayList hashtagsList = new ArrayList();
    public String body;
    public String createdAt;
    public User user;
    public String relativeTimestamp;
    //public ArrayList hashtagsList = new ArrayList<>();
    public String imageUrl;

    public Tweet(){}

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.relativeTimestamp = tweet.getRelativeTimeAgo(tweet.createdAt);
        JSONObject entities = jsonObject.getJSONObject("entities");
        try{
            JSONArray hashtags = entities.getJSONArray("media");
            for (Integer i = 0; i<hashtags.length(); i++){
                if (hashtags.getJSONObject(i).getString("type").equals("photo")){
                    tweet.imageUrl = hashtags.getJSONObject(i).getString("media_url_https");
                    break;
                }
            }

            Log.e("parsing media", hashtags.toString() + tweet.body);
            Log.e("parsing media", "image url: " + tweet.imageUrl );
        }catch (JSONException e){
            Log.e("parsing media", "no media found: " + tweet.body);
        }


        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i=0;i<jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (ParseException e) {
            Log.i("calculatingRTimestamp", "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }
}
