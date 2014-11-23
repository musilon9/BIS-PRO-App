
package app.com.bisnode;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;


import app.com.bisnode.tabfragments.ContactsFragment;
import app.com.bisnode.tabfragments.FavouriteFragment;
import app.com.bisnode.tabfragments.FreeInfoFragment;
import app.com.bisnode.tabfragments.HistoryFragment;
import app.com.bisnode.tabfragments.ManagementFragment;
import app.com.bisnode.tabfragments.SearchFragment;
import app.com.bisnode.tablisteners.ActivitiesListener;
import app.com.bisnode.tablisteners.CheckCompanyListener;
import app.com.bisnode.tablisteners.ContactsListener;
import app.com.bisnode.tablisteners.FavouriteListener;
import app.com.bisnode.tablisteners.HistoryListener;
import app.com.bisnode.tablisteners.InfoListener;
import app.com.bisnode.tablisteners.ManagementListener;
import app.com.bisnode.tablisteners.SearchListener;


public class CompanyActivity extends FragmentActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**+
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        displayTabs(mSectionsPagerAdapter);
    }

    private void displayTabs(SectionsPagerAdapter adapter) {
        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(adapter.getPageTitle(i))
                            .setTabListener(getTabListener(i)));
        }
    }

    private ActionBar.TabListener getTabListener(int i) {
        ActionBar.TabListener listener = null;
        switch (i) {
            case 0 :
                listener = new ContactsListener(mViewPager);
                break;
            case 1 :
                listener = new ActivitiesListener(mViewPager);
                break;
            case 2 :
                listener = new ManagementListener(mViewPager);
                break;
            case 3 :
                listener = new CheckCompanyListener(mViewPager);
                break;
        }

        return listener;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment actualFragment = null;
            switch (position) {
                case 0:
                    actualFragment = ContactsFragment.newInstance(position + 1);
                    break;
                case 1:
                    actualFragment = ManagementFragment.newInstance(position + 1);
                    break;
                case 2:
                    actualFragment =  FavouriteFragment.newInstance(position + 1);
                    break;
            }
            return actualFragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.contacts).toUpperCase(l);
                case 1:
                    return getString(R.string.detail_info).toUpperCase(l);
                case 2:
                    return getString(R.string.check_company).toUpperCase(l);
            }
            return null;
        }
    }

}
