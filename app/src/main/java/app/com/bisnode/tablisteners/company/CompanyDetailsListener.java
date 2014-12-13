package app.com.bisnode.tablisteners.company;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import app.com.bisnode.tablisteners.PageMover;

public class CompanyDetailsListener extends PageMover implements ActionBar.TabListener {

    public CompanyDetailsListener(ViewPager viewPager) {
        super(viewPager);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
