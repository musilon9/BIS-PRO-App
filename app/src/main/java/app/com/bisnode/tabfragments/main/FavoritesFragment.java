package app.com.bisnode.tabfragments.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import app.com.bisnode.CompanyActivity;
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
        return rootView;
    }

}
