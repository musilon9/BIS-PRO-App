package app.com.bisnode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;


public class FavouriteAdapter extends ArrayAdapter<CompanyModel> {

    public FavouriteAdapter(Context context, int resource, List<CompanyModel> companyModels) {
        super(context, resource, companyModels);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            rowView = LayoutInflater.from(this.getContext()).inflate(R.layout.favourite_list_item, null);
        }
        final CompanyModel companyModel = getItem(position);
        TextView title = (TextView) rowView.findViewById(R.id.item_title);
        TextView location = (TextView) rowView.findViewById(R.id.item_location);
        title.setText(companyModel.getName());
        location.setText(companyModel.getLocation());
        ImageView image = (ImageView) rowView.findViewById(R.id.favourite_icon);
        image.setClickable(true);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(companyModel);
            }
        });
        return rowView;
    }

    public void delete(int pos) {

    }


}
