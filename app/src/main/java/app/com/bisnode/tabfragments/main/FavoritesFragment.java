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
import app.com.bisnode.adapters.FavoritesAdapter;
import app.com.bisnode.onclicklisteners.DatabaseHandler;
import app.com.bisnode.tabfragments.PlaceHolderFragment;

public class FavoritesFragment extends PlaceHolderFragment {

    public static FavoritesFragment newInstance(int sectionNumber) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        List<CompanyModel> lis = new DatabaseHandler(this.getActivity().getApplicationContext()).getFavourites();
        ListView expListView = (ListView) rootView.findViewById(R.id.favouriteListView);
        ListAdapter listAdapter = new FavoritesAdapter(MyApplication.getAppContext(), R.layout.list_item_favorites, lis);
        expListView.setAdapter(listAdapter);
        return rootView;
    }

}
