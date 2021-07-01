package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;
    TwitterClient client;
    public int REPLY_REQUEST_CODE = 30;

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
        // declaring list view images
        ImageView tweetImg;
        ImageButton reply;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvRelativeTimestamp = itemView.findViewById(R.id.tvRelativeTimestamp);
            tweetImg = itemView.findViewById(R.id.tweetImg);
            reply = itemView.findViewById(R.id.replyButton);
        }

        public void bind(final Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            tvRelativeTimestamp.setText("· " + tweet.relativeTimestamp);
            Glide.with(context)
                    .load(tweet.user.profileImageUrl)
                    .transform( new RoundedCornersTransformation(20, 0))
                    .into(ivProfileImage);
            //Glide.with(context).load("https://www.rd.com/wp-content/uploads/2020/07/00_OPENER-Final.jpg").into(ivProfileImage);
            if(tweet.imageUrl != null){
                Glide.with(context).load(tweet.imageUrl)
                        .transform( new RoundedCornersTransformation(60, 0))
                        .into(tweetImg);
                tweetImg.setVisibility(View.VISIBLE);
            }else {
                tweetImg.setVisibility(View.GONE);
            }
            Glide.with(context).load(R.drawable.reply_icon).into((ImageView) itemView.findViewById(R.id.replyButton));

            //Glide.with(context).load(itemView.findViewById()).into();
            reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ComposeReplyActivity.class);
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    ((Activity) context).startActivityForResult(intent,REPLY_REQUEST_CODE);
                }

            });
            //

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
