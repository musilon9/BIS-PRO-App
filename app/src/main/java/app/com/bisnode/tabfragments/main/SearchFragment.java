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
        //Toast.makeText(getActivity(), "Stiskněte Hledat", Toast.LENGTH_LONG).show();
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
                                                actualModel.optLong("regNbr")!= 0 ? R.drawable.ic_company : R.drawable.ic_person,
                                                actualModel.optString("name"),
                                                actualModel.optString("town")));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ListAdapter listAdapter = new SearchAdapter(MyApplication.getAppContext(), R.layout.list_item, lis);
                                expListView.setAdapter(listAdapter);
                                //Toast.makeText(getActivity(), "Zvolte MADETA, a.s.", Toast.LENGTH_LONG).show();
                                expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent showCompany = null;
                                        CompanyModel selectedCompany = (CompanyModel) parent.getItemAtPosition(position);
                                        if (selectedCompany.getName().equals("MADETA a. s.")) {
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

    private void searchRequest(String query) {
        // Tag used to cancel the request
        String tag_json_arry = "json_array_req";

        String url = "https://gnosus.bisnode.cz/magnusweb-rest/query/simple/subject.fulltext?q=" + query;
        final String sid = "gnosus/56url2test28134";

        final String TAG = "Volley";
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        }) {
            /**
             * Passing session ID header
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("sid", sid);
                return headers;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

    }

}
