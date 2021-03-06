
package app.com.bisnode;

import java.util.Locale;

import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;


import app.com.bisnode.dialogs.FilterDialog;
import app.com.bisnode.onclicklisteners.DatabaseHandler;
import app.com.bisnode.tabfragments.main.FavoritesFragment;
import app.com.bisnode.tabfragments.main.HistoryFragment;
import app.com.bisnode.tabfragments.main.SearchFragment;
import app.com.bisnode.tablisteners.main.FavoritesListener;
import app.com.bisnode.tablisteners.main.HistoryListener;
import app.com.bisnode.tablisteners.main.SearchListener;


public class MainActivity extends FragmentActivity {

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
    private int mStackLevel = 4;
    private Fragment searchFragment;

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

        // Select the corresponding tab when the user swipes between pages with a touch gesture
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });

        displayTabs();
    }

    private void displayTabs() {
        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(getIconId(i))
                            .setTabListener(getTabListener(i)));
        }
    }

    private int getIconId(int i) {
        int id = 0;
        switch (i) {
            case 0:
                id = R.drawable.ic_action_search;
                break;
            case 1:
                id = R.drawable.ic_action_time;
                break;
            case 2:case 3:
                id = R.drawable.ic_action_favorite;
                break;
        }
        return id;
    }

    private ActionBar.TabListener getTabListener(int i) {
        ActionBar.TabListener listener = null;
        switch (i) {
            case 0 :
                listener = new SearchListener(mViewPager);
                break;
            case 1 :
                listener = new HistoryListener(mViewPager);
                break;
            case 2 :
                listener = new FavoritesListener(mViewPager);
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
            logout();
        }
        if(id == R.id.action_search) {
            openFilter();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        SharedPreferences settings = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(LoginActivity.LOGGED_PERMANENTLY, false);
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void openFilter() {
        mStackLevel++;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        FilterDialog newFragment = FilterDialog.newInstance(mStackLevel, (SearchFragment)searchFragment);
        newFragment.show(ft, "dialog");
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
                    actualFragment = SearchFragment.newInstance(position + 1);
                    searchFragment = actualFragment;
                    break;
                case 1:
                    actualFragment = HistoryFragment.newInstance(position + 1);
                    break;
                case 2:
                    actualFragment =  FavoritesFragment.newInstance(position + 1);
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
                    return getString(R.string.search).toUpperCase(l);
                case 1:
                    return getString(R.string.history).toUpperCase(l);
                case 2:
                    return getString(R.string.favourite).toUpperCase(l);
                case 3:
                    return getString(R.string.company).toUpperCase(l);
            }
            return null;
        }
    }


}
