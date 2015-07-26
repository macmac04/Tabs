package com.app.macky.droidgency;

/**
 * Created by Macsanity on 7/24/2015.
 */

import android.app.ListActivity;
import android.text.method.LinkMovementMethod;
import android.widget.ArrayAdapter;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class TwitterAsyncTask extends AsyncTask<Object, Void, ArrayList<TwitterTweet>> {
    ListActivity callerActivity;

    final static String TWITTER_API_KEY = "UjqqnYRTxH6jOozcPYlRJKS";
    final static String TWITTER_API_SECRET = "rWjmk35PyGKaTEWzSZj0AN73d5dflOpwlBRfYozxw341R";

    @Override
    protected ArrayList<TwitterTweet> doInBackground(Object... params) {
        ArrayList<TwitterTweet> twitterTweets = null;
        callerActivity = (ListActivity) params[1];
        if (params.length > 0) {
            TwitterFeedService twitterAPI = new TwitterFeedService(TWITTER_API_KEY,TWITTER_API_SECRET);
            twitterTweets = twitterAPI.getTwitterTweets(params[0].toString());
        }
        return twitterTweets;
    }

    @Override
    protected void onPostExecute(ArrayList<TwitterTweet> twitterTweets) {
        ArrayAdapter<TwitterTweet> adapter =
                new ArrayAdapter<TwitterTweet>(callerActivity, R.layout.list_feed,
                        R.id.listTextView, twitterTweets);
        callerActivity.setListAdapter(adapter);
        ListView lv = callerActivity.getListView();
        lv.setDividerHeight(0);
        //lv.setDivider(this.getResources().getDrawable(android.R.color.transparent));
     //olor(callerActivity.getResources().getColor(R.color.Twitter_blue));
    }
}