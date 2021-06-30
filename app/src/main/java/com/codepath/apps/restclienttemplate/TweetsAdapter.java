package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;

    //pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }
    //for each row inflate layout

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // bind values based on the position to the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvRelativeTimestamp;
        ListView lvImages;
        // declaring list view images
        ArrayList<String> images;
        ImageView tweetImg;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRelativeTimestamp = itemView.findViewById(R.id.tvRelativeTimestamp);
            //lvImages = itemView.findViewById(R.id.lvImages);
            tweetImg = itemView.findViewById(R.id.tweetImg);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvRelativeTimestamp.setText("· " + tweet.relativeTimestamp);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            //Glide.with(context).load("https://www.rd.com/wp-content/uploads/2020/07/00_OPENER-Final.jpg").into(ivProfileImage);
            if(tweet.imageUrl != null){
                Glide.with(context).load(tweet.imageUrl).into(tweetImg);
                //tweetImg.setVisibility(View.VISIBLE);
            }else {
                tweetImg.setVisibility(View.GONE);
            }
            //assigning an adapter to the list view
            /*images = new ArrayList<>();
            images.add(tweet.imageUrl);
            adapter = new TweetsAdapter(this, tweets);
            // init the list of tweets and adapter
            // Recycler view setup: layout manager and the adapter
            rvTweets.setLayoutManager(new LinearLayoutManager(this));
            rvTweets.setAdapter(adapter);
            populateHomeTimeline();*/
        }
    }

    /*public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }*/
}
