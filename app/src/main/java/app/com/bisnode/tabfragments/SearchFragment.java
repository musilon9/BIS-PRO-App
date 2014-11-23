package app.com.bisnode.tabfragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app.com.bisnode.CompanyActivity;
import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.SearchAdapter;
import app.com.bisnode.fakedata.FakeSearch;
import app.com.bisnode.tablisteners.SearchItemOnClickListener;
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
        final View rootView = inflater.inflate(R.layout.search_fragment, container, false);
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

}
