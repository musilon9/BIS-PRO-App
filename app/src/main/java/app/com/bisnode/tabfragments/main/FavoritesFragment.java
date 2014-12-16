package app.com.bisnode.tabfragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import app.com.bisnode.AppController;
import app.com.bisnode.MyApplication;
import app.com.bisnode.R;
import app.com.bisnode.adapters.CompanyModel;
import app.com.bisnode.adapters.FavoritesAdapter;
import app.com.bisnode.fakedata.FakeFavorites;
import app.com.bisnode.tabfragments.PlaceHolderFragment;
import app.com.bisnode.utils.ModelUtils;

public class FavoritesFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
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
        List<CompanyModel> lis = ModelUtils.convertCompanyToCompanyModel(FakeFavorites.list);
        ListView expListView = (ListView) rootView.findViewById(R.id.favouriteListView);
        ListAdapter listAdapter = new FavoritesAdapter(MyApplication.getAppContext(), R.layout.list_item_favorites, lis);
        expListView.setAdapter(listAdapter);
        return rootView;
    }

}
