package app.com.bisnode.tabfragments.company;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.bisnode.R;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.objects.Scoring;
import app.com.bisnode.requests.CustomJsonArrayRequest;
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

//        setIndicatorsContent(rootView, com);
        setTurnoverContent(rootView, com);
        setEmployeesContent(rootView, com);

        setContents(rootView);

        return rootView;
    }

    private void setContents(View v) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        setIndicatorsContent(v, queue);
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

    private void setIndicatorsContent(View v, RequestQueue queue) {
        TextView scoring = (TextView) v.findViewById(R.id.scoringValue);
        TextView paymentIndex = (TextView) v.findViewById(R.id.indexValue);
        TextView scoringDesc = (TextView) v.findViewById(R.id.scoringDescription);
        TextView indexDesc = (TextView) v.findViewById(R.id.indexDescription);
        requestIndicators(scoring, paymentIndex, scoringDesc, indexDesc, queue);
    }

    private void requestIndicators(final TextView scoring, final TextView paymentIndex,
                                   final TextView scoringDesc, final TextView indexDesc, RequestQueue queue) {

        String url = String.format(
                getString(R.string.requestIndicators),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject indicators = response.getJSONObject(0);
                    String scoringValue = indicators.optString(getString(R.string.jsonFieldScoringCode));
                    int indexValue = indicators.optInt(getString(R.string.jsonFieldPaymentIndex));
                    scoring.setText(scoringValue);
                    scoring.setTextColor(Color.parseColor(Scoring.valueOf(scoringValue).getColorCode()));
                    scoringDesc.setText(indicators.optString(getString(R.string.jsonFieldScoringDesc)));
                    paymentIndex.setText(Integer.toString(indexValue));
                    setIndexColor(paymentIndex, indexValue);
                    indexDesc.setText(indicators.optString(getString(R.string.jsonFieldPaymentIndexDesc)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
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

}
