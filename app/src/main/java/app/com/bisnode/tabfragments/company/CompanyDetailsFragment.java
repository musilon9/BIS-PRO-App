package app.com.bisnode.tabfragments.company;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.bisnode.R;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.tabfragments.PlaceHolderFragment;

public class CompanyDetailsFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CompanyDetailsFragment newInstance(int sectionNumber) {
        CompanyDetailsFragment fragment = new CompanyDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CompanyDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.management_fragment, container, false);
        Company com = FakeSearch.getExample();
        setSectorAContent(rootView, com);
        setSectorBContent(rootView, com);
        return rootView;
    }

    private void setSectorAContent(View v, Company com) {
        TextView activities = (TextView) v.findViewById(R.id.activities);
        String act = "";
        for (int i = 0; i < com.getActivities().size(); i++) {
            act += (i == 0 ? "" : "\n") + com.getActivities().get(i);
        }
        activities.setText(act);
    }

    private void setSectorBContent(View v, Company com) {
        TextView management = (TextView) v.findViewById(R.id.management);
        String man = "";
        for (int i = 0; i < com.getManagement().size(); i++) {
            man += (i == 0 ? "" : "\n") + com.getManagement().get(i).getName();
        }
        management.setText(man);
    }

}
