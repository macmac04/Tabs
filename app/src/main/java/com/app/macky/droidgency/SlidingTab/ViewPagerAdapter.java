package com.app.macky.droidgency.SlidingTab;

/**
 * Created by Macsanity on 7/11/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.macky.droidgency.ContactFragment;
import com.app.macky.droidgency.EmergencyHotlines;
import com.app.macky.droidgency.HomeFragment;
import com.app.macky.droidgency.UploadActivity;

/**
 * Created by hp1 on 21-01-2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if (position == 0) // if the position is 0 we are returning the First tab
        {
            HomeFragment tab1 = new HomeFragment();
            return tab1;
        }
        if (position == 1) // if the position is 0 we are returning the First tab
        {
            ContactFragment tab2 = new ContactFragment();
            return tab2;
        }
        if (position == 2) // if the position is 0 we are returning the First tab
        {
            UploadActivity tab3 = new UploadActivity();

            return tab3;
        }
       if (position == 3) // if the position is 0 we are returning the First tab
       {
           EmergencyHotlines tab4 = new EmergencyHotlines();
            return tab4;
        }
        //if (position == 4) // if the position is 0 we are returning the First tab
        //{
          //  UserGuideTab tab5 = new UserGuideTab();
            //return tab5;
        //}

        else {
            return null;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {

        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {

        return NumbOfTabs;
    }

}