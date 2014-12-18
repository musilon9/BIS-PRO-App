package app.com.bisnode.tabfragments.main;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.bisnode.AppController;
import app.com.bisnode.CompanyActivity;
import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.SearchAdapter;
import app.com.bisnode.requests.CustomJsonArrayRequest;
import app.com.bisnode.tabfragments.PlaceHolderFragment;

public class SearchFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SearchFragment newInstance(int sectionNumber) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        Button button = (Button) rootView.findViewById(R.id.searchButton);
        final EditText queryField = (EditText) rootView.findViewById(R.id.searchField);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                String url = getString(R.string.requestSearch) + queryField.getText();

                CustomJsonArrayRequest jsObjRequest = new CustomJsonArrayRequest
                        (url, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                ListView expListView = (ListView) rootView.findViewById(R.id.listView);
                                List<CompanyModel> lis = null;
                                try {
                                    lis = new ArrayList<>(response.length());
                                    JSONObject actualModel;
                                    for(int i = 0; i < response.length(); i++) {
                                        actualModel = response.getJSONObject(i);
                                        lis.add(new CompanyModel(actualModel.optLong("entId"),
                                                getIconForType(actualModel.optString("typeCd")),
                                                actualModel.optString("name"),
                                                actualModel.optString("town")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ListAdapter listAdapter = new SearchAdapter(MyApplication.getAppContext(), R.layout.list_item, lis);
                                expListView.setAdapter(listAdapter);
                                expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent showCompany = null;
                                        CompanyModel selectedCompany = (CompanyModel) parent.getItemAtPosition(position);
                                        if (selectedCompany.getName().equals("MADETA a. s.") || true) {
                                            showCompany = new Intent(getActivity(), CompanyActivity.class);
                                            showCompany.putExtra("id", selectedCompany.getId());
                                            showCompany.putExtra("name", selectedCompany.getName());
                                        }
                                        if (showCompany != null) startActivity(showCompany);
                                    }
                                });
                            }
                        }
                        , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), getString(R.string.serverError), Toast.LENGTH_LONG).show();
                            }
                        });
                queue.add(jsObjRequest);


            }
        });
        return rootView;
    }

    private int getIconForType(String type) {
        if (type.charAt(3) == '0') return R.drawable.ic_company;
        else if (type.charAt(5) == '1') return R.drawable.ic_person;
        else return R.drawable.ic_ent_person;
    }

}
