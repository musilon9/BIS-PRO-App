package app.com.bisnode.tabfragments.company;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.bisnode.R;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.objects.Scoring;
import app.com.bisnode.tabfragments.PlaceHolderFragment;

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
        View rootView = inflater.inflate(R.layout.fragment_company_check, container, false);
        Company com = FakeSearch.getExample();

        setIndicatorsContent(rootView, com);
        setTurnoverContent(rootView, com);
        setEmployeesContent(rootView, com);

        return rootView;
    }

    private void setEmployeesContent(View rootView, Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.employeesBlock);
        TextView[] labels = new TextView[] {
                (TextView) block.findViewById(R.id.label_blockFour_1A),
                (TextView) block.findViewById(R.id.label_blockFour_2A),
                (TextView) block.findViewById(R.id.label_blockFour_1B),
                (TextView) block.findViewById(R.id.label_blockFour_2B)
        };
        TextView[] values = new TextView[] {
                (TextView) block.findViewById(R.id.info_blockFour_1A),
                (TextView) block.findViewById(R.id.info_blockFour_2A),
                (TextView) block.findViewById(R.id.info_blockFour_1B),
                (TextView) block.findViewById(R.id.info_blockFour_2B)
        };

        TextView title = (TextView) block.findViewById(R.id.title_blockFour);
        title.setText(R.string.employees_sectionTitle);

        int startYear = com.getEmployees().firstKey();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(String.format("%d", startYear + i));
            values[i].setText(String.format("%d",
                    com.getEmployees().get(startYear + i)));
        }

        ImageView trend = (ImageView) block.findViewById(R.id.trendArrow);
        if (com.getEmployees().get(startYear + labels.length - 1) <
                com.getEmployees().get(startYear + labels.length - 2))
            trend.setImageResource(R.drawable.ic_arrow_down);
    }

    private void setTurnoverContent(View rootView, Company com) {
        LinearLayout block = (LinearLayout) rootView.findViewById(R.id.turnoverBlock);
        TextView[] labels = new TextView[] {
                (TextView) block.findViewById(R.id.label_blockFour_1A),
                (TextView) block.findViewById(R.id.label_blockFour_2A),
                (TextView) block.findViewById(R.id.label_blockFour_1B),
                (TextView) block.findViewById(R.id.label_blockFour_2B)
        };
        TextView[] values = new TextView[] {
                (TextView) block.findViewById(R.id.info_blockFour_1A),
                (TextView) block.findViewById(R.id.info_blockFour_2A),
                (TextView) block.findViewById(R.id.info_blockFour_1B),
                (TextView) block.findViewById(R.id.info_blockFour_2B)
        };

        TextView title = (TextView) block.findViewById(R.id.title_blockFour);
        title.setText(R.string.turnover_sectionTitle);

        int startYear = com.getTurnover().firstKey();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(String.format("%d", startYear + i));
            values[i].setText(String.format("%d",
                    com.getTurnover().get(startYear + i) / 1000) + " mil.");
        }

        ImageView trend = (ImageView) block.findViewById(R.id.trendArrow);
        if (com.getTurnover().get(startYear + labels.length - 1) <
                com.getTurnover().get(startYear + labels.length - 2))
            trend.setImageResource(R.drawable.ic_arrow_down);
    }

    private void setIndicatorsContent(View v, Company com) {
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
