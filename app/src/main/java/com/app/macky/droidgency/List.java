package com.app.macky.droidgency;

/**
 * Created by Macsanity on 7/25/2015.
 */
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;



public class List extends ListActivity {

    final static String twitterScreenName = "BBCNews";
    final static String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
        if (androidNetworkUtility.isConnected(this)) {
            new TwitterAsyncTask().execute(twitterScreenName,this);
        } else {
            Log.v(TAG, "Network not Available!");
        }
    }
}