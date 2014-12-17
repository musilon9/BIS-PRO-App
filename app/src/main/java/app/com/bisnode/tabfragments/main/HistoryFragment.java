package app.com.bisnode.tabfragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.SearchAdapter;
import app.com.bisnode.onclicklisteners.DatabaseHandler;
import app.com.bisnode.tabfragments.PlaceHolderFragment;

public class HistoryFragment extends PlaceHolderFragment {

    public static HistoryFragment newInstance(int sectionNumber) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        ListView expListView = (ListView) rootView.findViewById(R.id.historyListView);
        List<CompanyModel> list = new DatabaseHandler(this.getActivity().getApplicationContext()).getHistory();
        ListAdapter listAdapter = new SearchAdapter(MyApplication.getAppContext(), R.layout.list_item_favorites, list);
        expListView.setAdapter(listAdapter);
        return rootView;
    }

}
