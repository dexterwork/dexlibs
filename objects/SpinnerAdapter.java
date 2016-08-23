package dexter.studio.rsl.objects;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dexter on 2016/8/23.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    //sample code
//    List<String> list = new ArrayList<>();
//
//    for (CityEntity.City city : cityEntity.results.City_List) {
//        list.add(city.city_Name);
//    }
//    list.add(activity.getString(R.string.city)); <---------------------------------- hint text here
//
//    final SpinnerAdapter adapter = new SpinnerAdapter(activity, list);
//
//
//    spinnerCity.setAdapter(adapter);
//    spinnerCity.setSelection(adapter.getCount()); <-------------------------------------- select hint position
//    spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        @Override
//        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            if (position >= adapter.getCount()) {
//                currentCity = -1;
//                return;
//            }
//            currentCity = position;
//            parseArea(cityEntity.results.City_List.get(position));
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> parent) {
//
//        }
//    });

    public SpinnerAdapter(Context context, List<String> objects) {
        this(context, android.R.layout.simple_spinner_item, objects);
    }

    public SpinnerAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        if (position == getCount()) {
            ((TextView) v.findViewById(android.R.id.text1)).setText("");
            ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
        }

        return v;
    }

    @Override
    public int getCount() {
        return super.getCount() - 1;
    }
}
