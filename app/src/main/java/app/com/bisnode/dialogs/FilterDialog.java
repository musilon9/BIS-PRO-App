package app.com.bisnode.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import app.com.bisnode.R;
import app.com.bisnode.tabfragments.main.SearchFragment;

    public class FilterDialog extends DialogFragment {

    SearchFragment searchFragment;

    public static FilterDialog newInstance(int sectionNumber, SearchFragment searchFragment) {
        FilterDialog f = new FilterDialog();
        f.setSearchFragment(searchFragment);
        Bundle args = new Bundle();
        args.putInt("num", sectionNumber);
        f.setArguments(args);
        return f;
    }

    public FilterDialog() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.filter_dialog, null);
        ImageButton button = (ImageButton) rootView.findViewById(R.id.company_button);
        button.setClickable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.performFiltering(String.valueOf(R.drawable.ic_company));
            }
        });

        ImageButton button2 = (ImageButton) rootView.findViewById(R.id.person_button);
        button2.setClickable(true);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.performFiltering(String.valueOf(R.drawable.ic_person));
            }
        });

        ImageButton button3 = (ImageButton) rootView.findViewById(R.id.private_person_button);
        button3.setClickable(true);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.performFiltering(String.valueOf(R.drawable.ic_ent_person));
            }
        });
        builder.setView(rootView);

        return builder.create();
    }

    public void setSearchFragment(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

}
