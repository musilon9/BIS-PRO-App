package app.com.bisnode.tabfragments.company;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.SearchAdapter;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.objects.Company;
import app.com.bisnode.requests.CustomJsonArrayRequest;
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
        View rootView = inflater.inflate(R.layout.fragment_company_details, container, false);
        requestPeople(rootView);
        return rootView;
    }

    private void requestPeople(final View v) {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = String.format(getString(R.string.requestPeople),
                getActivity().getIntent().getLongExtra("id", 0));
        CustomJsonArrayRequest request = new CustomJsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ListView expListView = (ListView) v.findViewById(R.id.managementList);
                List<CompanyModel> lis = null;
                try {
                    lis = new ArrayList<>(response.length());
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject person = response.getJSONObject(i);
                        lis.add(new CompanyModel(0, R.drawable.ic_person, person.optString(getString(R.string.jsonFieldValue)),
                                person.optString(getString(R.string.jsonFieldType))));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ListAdapter listAdapter = new SearchAdapter(MyApplication.getAppContext(), R.layout.list_item, lis);
                expListView.setAdapter(listAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);
    }

}
