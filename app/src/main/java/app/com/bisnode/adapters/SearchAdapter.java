package app.com.bisnode.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class SearchAdapter extends ArrayAdapter<String> {

    Map<String, Integer> mIdMap = new HashMap<String, Integer>();

    public SearchAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
