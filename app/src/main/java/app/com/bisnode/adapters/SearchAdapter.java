package app.com.bisnode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.bisnode.MyApplication;
import app.com.bisnode.R;


public class SearchAdapter extends ArrayAdapter<CompanyModel> implements Filterable {

    private final List<CompanyModel> items;

    public SearchAdapter(Context context, int resource, List<CompanyModel> companyModels) {
        super(context, resource, companyModels);
        this.items = new ArrayList<>();
        this.items.addAll(companyModels);
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

    public Filter getFilter() {
        return new ItemFilter();
    }

    private class ItemFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();
            String[] filters = filterString.split(":");
            FilterResults results = new FilterResults();

            final List<CompanyModel> list = items;

            int count = list.size();
            final ArrayList<CompanyModel> nlist = new ArrayList<>(count);

            CompanyModel companyModel;

            for (int i = 0; i < count; i++) {
                companyModel = list.get(i);
                for(String filter : filters) {
                    if (String.valueOf(companyModel.getIcon()).equals(filter)) {
                        nlist.add(companyModel);
                    }
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((ArrayList<CompanyModel>)results.values);
            notifyDataSetChanged();
        }

    }


}
