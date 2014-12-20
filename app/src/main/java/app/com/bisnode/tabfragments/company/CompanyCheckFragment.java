package app.com.bisnode.tabfragments.company;


import android.graphics.Color;
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

import java.util.HashMap;
import java.util.TreeMap;

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

        setContents(rootView);

        return rootView;
    }

    private void setContents(View v) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        setIndicatorsContent(v, queue);
        requestCompanySize(v, queue);
    }

    private void setIndicatorsContent(View v, RequestQueue queue) {
        TextView scoring = (TextView) v.findViewById(R.id.scoringValue);
        TextView paymentIndex = (TextView) v.findViewById(R.id.indexValue);
        TextView scoringDesc = (TextView) v.findViewById(R.id.scoringDescription);
        TextView indexDesc = (TextView) v.findViewById(R.id.indexDescription);
        requestIndicators(v, scoring, paymentIndex, scoringDesc, indexDesc, queue);
    }

    private void requestIndicators(final View v, final TextView scoring, final TextView paymentIndex,
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
                    checkNegativeIndicators(v, indicators);
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

    private void checkNegativeIndicators(View v, JSONObject indicators) {
        int sum = 0;
        int[] indicatorFields = new int[] {R.string.jsonFieldNegInsolvence, R.string.jsonFieldNegInsolvenceHis,
                R.string.jsonFieldNegExekuce, R.string.jsonFieldNegExekuceHis,
                R.string.jsonFieldNegLikvidace, R.string.jsonFieldNegLikvidaceeHis,
                R.string.jsonFieldNegNespPlatce, R.string.jsonFieldNegNespPlatceHis,
                R.string.jsonFieldNegDluhy, R.string.jsonFieldNegDluhyHis,
        };
        int[] indicatorLabels = new int[] {R.string.indicatorInsolvence, R.string.indicatorExekuce,
            R.string.indicatorLikvidace, R.string.indicatorNespPlatce, R.string.indicatorDluhy};
        LinearLayout[] indicatorLines = new LinearLayout[] {
                (LinearLayout) v.findViewById(R.id.indicator_Insolvence),
                (LinearLayout) v.findViewById(R.id.indicator_Exekuce),
                (LinearLayout) v.findViewById(R.id.indicator_Likvidace),
                (LinearLayout) v.findViewById(R.id.indicator_NespPlatce),
                (LinearLayout) v.findViewById(R.id.indicator_Dluhy),
                (LinearLayout) v.findViewById(R.id.indicator_OK)
        };
        for (int i = 0; i < indicatorFields.length; i += 2) {
            int currentCount = indicators.optInt(getString(indicatorFields[i]));
            int historicalCount = indicators.optInt(getString(indicatorFields[i+1]));
            LinearLayout neg = indicatorLines[i/2];
            TextView text = (TextView) neg.findViewById(R.id.indicatorText);
            if (i == 6) text.setTextSize(16); // "nespolehlivy platce DPH" is too long
            if (currentCount > 0) {
                sum += currentCount;
                neg.setVisibility(View.VISIBLE);
                text.setText(String.format("%s (%d)", getString(indicatorLabels[i/2]), currentCount));
            } else if (historicalCount > 0) {
                sum += historicalCount;
                neg.setVisibility(View.VISIBLE);
                ImageView icon = (ImageView) neg.findViewById(R.id.indicatorIcon);
                icon.setImageResource(R.drawable.indicator_his);
                text.setText(String.format("%s (%s)", getString(indicatorLabels[i/2]), getString(R.string.historical)));
                text.setTextSize(16); // too long text
            }
        }
        if (sum == 0) {
            LinearLayout ok = indicatorLines[5];
            ok.setVisibility(View.VISIBLE);
            ImageView icon = (ImageView) ok.findViewById(R.id.indicatorIcon);
            icon.setImageResource(R.drawable.indicator_ok);
            TextView text = (TextView) ok.findViewById(R.id.indicatorText);
            text.setText(R.string.noNegatives);
            text.setTextSize(16);
        }
    }

    private void requestCompanySize(final View v, RequestQueue queue) {
        String url = String.format(
                getString(R.string.requestSize),
                getActivity().getIntent().getLongExtra("id", 0));
        final TreeMap<Integer, Long> turnover = new TreeMap<>();
        final TreeMap<Integer, Integer> employeeCount = new TreeMap<>();
        final TreeMap<Integer, Long> capital = new TreeMap<>();
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject yearObject = response.getJSONObject(i);
                        int year = Integer.parseInt(yearObject.optString(getString(R.string.jsonFieldYear)));
                        turnover.put(year, yearObject.optLong(getString(R.string.jsonFieldTurnover)));
                        employeeCount.put(year, yearObject.optInt(getString(R.string.jsonFieldEmployeeCount)));
                        capital.put(year, yearObject.optLong(getString(R.string.jsonFieldCapital)));
                    }
                    displayTurnover(v, turnover, response.getJSONObject(0).optString(getString(R.string.jsonFieldCurrency)));
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

    private void displayTurnover(View v, TreeMap<Integer, Long> turnover, String currency) {
        LinearLayout block = (LinearLayout) v.findViewById(R.id.turnoverBlock);
        TextView[] years = new TextView[] {(TextView) block.findViewById(R.id.year1),
                (TextView) block.findViewById(R.id.year2), (TextView) block.findViewById(R.id.year3)};
        TextView[] values = new TextView[] {(TextView) block.findViewById(R.id.value1),
                (TextView) block.findViewById(R.id.value2), (TextView) block.findViewById(R.id.value3)};
        int i = 2;  // last index
        for (int y: turnover.descendingKeySet()) {
            years[i].setText(Integer.toString(y));
            values[i].setText(readableTurnover(turnover.get(y)));
            i--;
            if (i < 0) break;
        }
        TextView title = (TextView) block.findViewById(R.id.title_blockFour);
        title.setText(R.string.turnover_sectionTitle);
        TextView yearLabel = (TextView) block.findViewById(R.id.label_year);
        yearLabel.setText(R.string.labelYear);
        TextView currencyLabel = (TextView) block.findViewById(R.id.label_value);
        currencyLabel.setText(currency);
    }

    private String readableTurnover(long value) {
        if (value < 999499) return String.format("%d %s", (value+500)/1000, getString(R.string.suffix_thousand));
        else if (value < 9994999) return String.format("%.2f %s", (double)value/1000000, getString(R.string.suffix_million));
        else if (value < 99949999) return String.format("%.1f %s", (double)value/1000000, getString(R.string.suffix_million));
        else if (value < 999499999) return String.format("%d %s", (value+500000)/1000000, getString(R.string.suffix_million));
        else if (value < 9994999999L) return String.format("%.2f %s", (double)value/1000000000, getString(R.string.suffix_billion));
        else if (value < 99949999999L) return String.format("%.1f %s", (double)value/1000000000, getString(R.string.suffix_billion));
        else return String.format("%d %s", (value+500000000)/1000000000, getString(R.string.suffix_billion));
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
