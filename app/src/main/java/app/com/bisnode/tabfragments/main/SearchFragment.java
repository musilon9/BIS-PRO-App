package app.com.bisnode.tabfragments.main;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import app.com.bisnode.CompanyActivity;
import app.com.bisnode.MainActivity;
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
    private View rootView;

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
        this.rootView = rootView;
        Button button = (Button) rootView.findViewById(R.id.searchButton);
        final EditText queryField = (EditText) rootView.findViewById(R.id.searchField);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(rootView.getWindowToken(), 0);

                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

                String url = getString(R.string.requestSearch) + Uri.encode(queryField.getText().toString());

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
                                        CompanyModel selectedCompany = (CompanyModel) parent.getItemAtPosition(position);
                                        Intent showCompany = new Intent(getActivity(), CompanyActivity.class);
                                        showCompany.putExtra("id", selectedCompany.getApiId());
                                        showCompany.putExtra("name", selectedCompany.getName());
                                        showCompany.putExtra("icon", selectedCompany.getIcon());
                                        showCompany.putExtra("location", selectedCompany.getLocation());
                                        showCompany.putExtra("is_company", selectedCompany.getIcon() == R.drawable.ic_company);
                                        startActivity(showCompany);
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

    public void performFiltering(String type) {
        ListView expListView = (ListView) rootView.findViewById(R.id.listView);
        if(expListView.getAdapter() == null) {
            Toast.makeText(getActivity(), getString(R.string.filter_error), Toast.LENGTH_LONG).show();
        } else {
            ((SearchAdapter)expListView.getAdapter()).getFilter().filter(type);
        }
    }

    private int getIconForType(String type) {
        if (type.charAt(3) == '0') return R.drawable.ic_company;
        else if (type.charAt(5) == '1') return R.drawable.ic_person;
        else return R.drawable.ic_ent_person;
    }

}
