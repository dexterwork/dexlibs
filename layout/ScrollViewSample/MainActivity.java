package studio.dexter.scrollviewsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity  {


    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
    }

    private void initLayout() {


        ArrayList<MBundle> bundleList = new ArrayList<MBundle>();
        for (int i = 0; i < 5; i++) {
            MBundle bundle = new MBundle();
            bundleList.add(bundle);
            bundle.setTitle(String.valueOf(i));
        }
        listView = (ListView) findViewById(R.id.listView);
        MAdapter adapter = new MAdapter(this, bundleList);
        listView.setAdapter(adapter);

//看要滾動的是 scrollview 還是 listview, 就要設定 onTouchListener
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.scrollView).getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

    }



}
