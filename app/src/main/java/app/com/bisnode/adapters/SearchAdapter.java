package app.com.bisnode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;


public class SearchAdapter extends ArrayAdapter<CompanyModel> {

    Map<Integer, CompanyModel> mIdMap = new HashMap<Integer, CompanyModel>();

    public SearchAdapter(Context context, int resource, List<CompanyModel> objects) {
        super(context, resource, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(i, objects.get(i));
        }
    }

    @Override
    public long getItemId(int position) {
        CompanyModel item = getItem(position);
        return item.getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            rowView = LayoutInflater.from(MyApplication.getAppContext()).inflate(R.layout.list_item, null);
        }
        CompanyModel companyModel = mIdMap.get(position);

        ImageView icon = (ImageView) rowView.findViewById(R.id.item_icon);
        icon.setImageResource(companyModel.getIcon());
        TextView title = (TextView) rowView.findViewById(R.id.item_title);
        TextView location = (TextView) rowView.findViewById(R.id.item_location);

        title.setText(companyModel.getName());
        location.setText(companyModel.getLocation());
        return rowView;
    }

}
