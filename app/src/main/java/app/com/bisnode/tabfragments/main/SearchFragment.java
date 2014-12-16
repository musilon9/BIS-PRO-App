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
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.bisnode.AppController;
import app.com.bisnode.CompanyActivity;
import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.SearchAdapter;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.tabfragments.PlaceHolderFragment;
import app.com.bisnode.utils.ModelUtils;

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
        Toast.makeText(getActivity(), "Stiskněte Hledat", Toast.LENGTH_LONG).show();
        Button button = (Button) rootView.findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((EditText) rootView.findViewById(R.id.searchField)).setText("madeta");
                ListView expListView = (ListView) rootView.findViewById(R.id.listView);
                List<CompanyModel> lis = ModelUtils.convertCompanyToCompanyModel(FakeSearch.list);
                ListAdapter listAdapter = new SearchAdapter(MyApplication.getAppContext(), R.layout.list_item, lis);
                expListView.setAdapter(listAdapter);
                //searchRequest("madeta");
                Toast.makeText(getActivity(), "Zvolte MADETA, a.s.", Toast.LENGTH_LONG).show();
                expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent showCompany = null;
                        if (position == 1)
                            showCompany = new Intent(getActivity(), CompanyActivity.class);
                        if (showCompany != null) startActivity(showCompany);
                    }
                });
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
