package app.com.bisnode.tabfragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.FavouriteAdapter;
import app.com.bisnode.fakedata.FakeFavorites;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.objects.Scoring;
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
        TextView scoring = (TextView) v.findViewById(R.id.scoringValue);
        scoring.setText(com.getScoring().toString());
        scoring.setTextColor(Color.parseColor(com.getScoring().getColorCode()));
        TextView paymentIndex = (TextView) v.findViewById(R.id.indexValue);
        paymentIndex.setText(String.format("%d", com.getPaymentIndex()));
        setIndexColor(paymentIndex, com.getPaymentIndex());
        TextView scoringDesc = (TextView) v.findViewById(R.id.scoringDescription);
        scoringDesc.setText(com.getScoring().getDescription());
        TextView indexDesc = (TextView) v.findViewById(R.id.indexDescription);
        setIndexDescription(indexDesc, com.getPaymentIndex());
    }

    private void setSectorCContent(View v, Company com) {
        TextView[] turnoverYears = new TextView[] {
                (TextView) v.findViewById(R.id.turnoverYear1),
                (TextView) v.findViewById(R.id.turnoverYear2),
                (TextView) v.findViewById(R.id.turnoverYear3),
                (TextView) v.findViewById(R.id.turnoverYear4)
        };
        TextView[] turnoverValues = new TextView[] {
                (TextView) v.findViewById(R.id.turnoverValue1),
                (TextView) v.findViewById(R.id.turnoverValue2),
                (TextView) v.findViewById(R.id.turnoverValue3),
                (TextView) v.findViewById(R.id.turnoverValue4)
        };
        TextView[] employeesYears = new TextView[] {
                (TextView) v.findViewById(R.id.employeesYear1),
                (TextView) v.findViewById(R.id.employeesYear2),
                (TextView) v.findViewById(R.id.employeesYear3),
                (TextView) v.findViewById(R.id.employeesYear4)
        };
        TextView[] employeesValues = new TextView[] {
                (TextView) v.findViewById(R.id.employeesValue1),
                (TextView) v.findViewById(R.id.employeesValue2),
                (TextView) v.findViewById(R.id.employeesValue3),
                (TextView) v.findViewById(R.id.employeesValue4)
        };
        int startYearT = com.getTurnover().firstKey();
        int startYearE = com.getEmployees().firstKey();
        for (int i = 0; i < 4; i++) {
            turnoverYears[i].setText(String.format("%d", startYearT + i));
            turnoverValues[i].setText(String.format("%d",
                        com.getTurnover().get(startYearT + i)/1000) + " mil.");
            employeesYears[i].setText(String.format("%d", startYearE + i));
            employeesValues[i].setText(String.format("%d",
                    com.getEmployees().get(startYearE + i)));
        }
    }

    private void setIndexColor(TextView tv, int index) {
        if (index < 1) tv.setTextColor(Color.parseColor(Scoring.AA.getColorCode()));
        else if (index < 5) tv.setTextColor(Color.parseColor(Scoring.A.getColorCode()));
        else if (index < 11) tv.setTextColor(Color.parseColor(Scoring.BBB.getColorCode()));
        else if (index < 21) tv.setTextColor(Color.parseColor(Scoring.BB.getColorCode()));
        else if (index < 31) tv.setTextColor(Color.parseColor(Scoring.B.getColorCode()));
        else if (index < 61) tv.setTextColor(Color.parseColor(Scoring.CCC.getColorCode()));
        else if (index < 91) tv.setTextColor(Color.parseColor(Scoring.C.getColorCode()));
        else if (index < 121) tv.setTextColor(Color.parseColor(Scoring.C.getColorCode()));
        else tv.setTextColor(Color.parseColor(Scoring.D.getColorCode()));
    }

    private void setIndexDescription(TextView tv, int index) {
        if (index < 1) tv.setText(R.string.payment0);
        else if (index < 5) tv.setText(R.string.payment1);
        else if (index < 11) tv.setText(R.string.payment2);
        else if (index < 21) tv.setText(R.string.payment3);
        else if (index < 31) tv.setText(R.string.payment4);
        else if (index < 61) tv.setText(R.string.payment5);
        else if (index < 91) tv.setText(R.string.payment6);
        else if (index < 121) tv.setText(R.string.payment7);
        else tv.setText(R.string.payment8);
    }

}
