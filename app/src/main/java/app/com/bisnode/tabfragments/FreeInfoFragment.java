package app.com.bisnode.tabfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.bisnode.R;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;

public class FreeInfoFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FreeInfoFragment newInstance(int sectionNumber) {
        FreeInfoFragment fragment = new FreeInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FreeInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.free_info_fragment, container, false);
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
        TextView address1 = (TextView) v.findViewById(R.id.addressLine1);
        address1.setText(com.getAddress());
        TextView address2 = (TextView) v.findViewById(R.id.addressLine2);
        address2.setText(com.getZip() + ", " + com.getCity());
    }

    private void setSectorCContent(View v, Company com) {

    }

}
