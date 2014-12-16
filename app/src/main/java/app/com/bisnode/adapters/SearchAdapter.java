package app.com.bisnode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.com.bisnode.AppController;
import app.com.bisnode.MyApplication;
import app.com.bisnode.R;


public class SearchAdapter extends ArrayAdapter<CompanyModel> {

    public SearchAdapter(Context context, int resource, List<CompanyModel> companyModels) {
        super(context, resource, companyModels);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            rowView = LayoutInflater.from(MyApplication.getAppContext()).inflate(R.layout.list_item, null);
        }
        CompanyModel companyModel = getItem(position);

        ImageView icon = (ImageView) rowView.findViewById(R.id.item_icon);
        icon.setImageResource(companyModel.getIcon());
        TextView title = (TextView) rowView.findViewById(R.id.item_title);
        TextView location = (TextView) rowView.findViewById(R.id.item_location);

        title.setText(companyModel.getName());
        location.setText(companyModel.getLocation());
        return rowView;
    }

}
