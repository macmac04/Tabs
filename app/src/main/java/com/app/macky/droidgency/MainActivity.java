package com.app.macky.droidgency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.app.macky.droidgency.ContactFragment;
import com.app.macky.droidgency.R;
import com.app.macky.droidgency.SlidingTab.*;

public class MainActivity extends ActionBarActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;

    CharSequence Titles[]={"Home","Contacts","Upload","User Guide"};
    int Numboftabs =4;


    IntentFilter intentFilter;

   private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
     @Override
       public void onReceive(Context context, Intent intent) {
           String msg = intent.getExtras().getString("sms");
            String type = msg.substring(ContactFragment.beginText.length(), ContactFragment.beginText.length());
            Log.i("SmsReceiverB", type);

       }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		/* Intent to filter */
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");
        //Intent nI = new Intent(android.content.Intent.ACTION_VIEW);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);


    }
    @Override
    protected void onResume(){
		/* Register receiver */
       registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
		/* Unregister receiver */
        super.onPause();
    }

    @Override
    protected void onDestroy() {
		/* Unregister receiver */
        unregisterReceiver(intentReceiver);
        super.onPause();
    }


    }


