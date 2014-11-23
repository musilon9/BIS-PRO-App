package app.com.bisnode.tabfragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.FavouriteAdapter;
import app.com.bisnode.fakedata.FakeFavorites;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.utils.ModelUtils;

public class CompanyCheckFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CompanyCheckFragment newInstance(int sectionNumber) {
        CompanyCheckFragment fragment = new CompanyCheckFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CompanyCheckFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.company_check_fragment, container, false);
        Company com = FakeSearch.getExample();
        setSectorAContent(rootView, com);
        setSectorBContent(rootView, com);
        setSectorCContent(rootView, com);
        return rootView;
    }

    private void setSectorAContent(View v, Company com) {
        TextView companyName = (TextView) v.findViewById(R.id.companyName);
        companyName.setText(com.getName());
        TextView companyICO = (TextView) v.findViewById(R.id.companyICO);
        companyICO.setText(getString(R.string.ico_label) + " " + com.getIC());
        TextView companyDIC = (TextView) v.findViewById(R.id.companyDIC);
        companyDIC.setText(getString(R.string.dic_label) + " " + com.getDIC());
    }

    private void setSectorBContent(View v, Company com) {
    }

    private void setSectorCContent(View v, Company com) {
    }

}
