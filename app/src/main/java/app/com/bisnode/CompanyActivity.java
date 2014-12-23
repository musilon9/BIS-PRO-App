
package app.com.bisnode;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;

import android.view.Menu;
import android.view.MenuItem;


import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.tabfragments.company.CompanyCheckFragment;
import app.com.bisnode.tabfragments.company.CompanyDetailsFragment;
import app.com.bisnode.tabfragments.company.ContactsFragment;
import app.com.bisnode.tablisteners.company.CompanyCheckListener;
import app.com.bisnode.tablisteners.company.CompanyDetailsListener;
import app.com.bisnode.tablisteners.company.ContactsListener;


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
        setTitle(getIntent().getStringExtra("name"));

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

        displayTabs(mSectionsPagerAdapter);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void displayTabs(SectionsPagerAdapter adapter) {
        final ActionBar actionBar = getActionBar();

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Add 3 tabs, specifying the tab's text and TabListener
        for (int i = 0; i < adapter.getCount(); i++) {
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
                listener = new CompanyDetailsListener(mViewPager);
                break;
            case 2 :
                listener = new CompanyCheckListener(mViewPager);
                break;
        }

        return listener;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_company, menu);
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

        // Respond to the action bar's Up/Home button
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
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
                    actualFragment = CompanyDetailsFragment.newInstance(position + 1);
                    break;
                case 2:
                    actualFragment =  CompanyCheckFragment.newInstance(position + 1);
                    break;
            }
            return actualFragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages for company and 1 for person
            if (getIntent().getBooleanExtra("is_company", false)) return 3;
            else return 1;
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
