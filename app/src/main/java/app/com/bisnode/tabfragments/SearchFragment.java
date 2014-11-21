package app.com.bisnode.tabfragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.SearchAdapter;
import app.com.bisnode.tablisteners.SearchItemOnClickListener;

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
                ((EditText) rootView.findViewById(R.id.searchField)).setText("aaaa");
                ListView expListView = (ListView) rootView.findViewById(R.id.listView);
                ArrayList<String> lis = new ArrayList<String>();
                lis.add("ahoj");
                lis.add("nene");
                ListAdapter listAdapter = new SearchAdapter(MyApplication.getAppContext(), R.layout.list_item, lis);
                expListView.setAdapter(listAdapter);
                expListView.setOnClickListener(new SearchItemOnClickListener());
            }
        });
        return rootView;
    }

}
