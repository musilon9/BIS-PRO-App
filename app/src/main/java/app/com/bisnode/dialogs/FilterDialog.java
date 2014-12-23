package app.com.bisnode.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.filter_dialog, null);

        final CheckBox button = (CheckBox) rootView.findViewById(R.id.checkBox);
        final CheckBox button2 = (CheckBox) rootView.findViewById(R.id.checkBox2);
        final CheckBox button3 = (CheckBox) rootView.findViewById(R.id.checkBox3);

        Button button_apply = (Button) rootView.findViewById(R.id.button_apply);
        button_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filter = "";
                if(button.isChecked()) {
                    filter+= String.valueOf(R.drawable.ic_company + ":");
                }
                if(button2.isChecked()) {
                    filter+= String.valueOf(R.drawable.ic_person + ":");
                }
                if(button3.isChecked()) {
                    filter+= String.valueOf(R.drawable.ic_ent_person + ":");
                }
                searchFragment.performFiltering(filter);
            }
        });
        builder.setView(rootView);

        return builder.create();
    }

    public void setSearchFragment(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

}
