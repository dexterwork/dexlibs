package studio.dexter.scrollviewsample;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by dexter on 2015/6/25.
 */
public class MAdapter extends BaseAdapter {
    ArrayList<MBundle> bundleList;
    MainActivity activity;

    public MAdapter(MainActivity activity, ArrayList<MBundle> bundleList) {
        this.activity = activity;
        this.bundleList = bundleList;
    }

    @Override
    public int getCount() {
        return bundleList.size();
    }

    @Override
    public Object getItem(int position) {
        return bundleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_view, parent, false);

        MHold hold = new MHold();
        hold.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        hold.tvTitle.setText(bundleList.get(position).getTitle());
        hold.subListView = (ListView) view.findViewById(R.id.subListView);
        ArrayList<MSubBundle> bundleList = new ArrayList<MSubBundle>();
        for (int i = 0; i < 5; i++) {
            MSubBundle bundle = new MSubBundle();
            bundleList.add(bundle);
            bundle.setMessage("Message " + i);
        }
        MSubAdapter adapter = new MSubAdapter(bundleList, activity);
        hold.subListView.setAdapter(adapter);

		//看要滾動的是 scrollview 還是 listview, 就要設定 onTouchListener
        hold.subListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    
        return view;
    }

   
}
