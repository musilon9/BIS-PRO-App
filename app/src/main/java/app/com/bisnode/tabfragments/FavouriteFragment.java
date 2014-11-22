package app.com.bisnode.tabfragments;


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
import app.com.bisnode.adapters.FavouriteAdapter;
import app.com.bisnode.fakedata.FakeFavorites;
import app.com.bisnode.utils.ModelUtils;

public class FavouriteFragment extends PlaceHolderFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FavouriteFragment newInstance(int sectionNumber) {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FavouriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favourite_fragment, container, false);
        List<CompanyModel> lis = ModelUtils.convertCompanyToCompanyModel(FakeFavorites.list);
        ListView expListView = (ListView) rootView.findViewById(R.id.favouriteListView);
        ListAdapter listAdapter = new FavouriteAdapter(MyApplication.getAppContext(), R.layout.favourite_list_item, lis);
        expListView.setAdapter(listAdapter);
        return rootView;
    }

}
