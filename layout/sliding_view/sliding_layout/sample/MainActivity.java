package com.skyking.televant;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.WindowManager;
import android.widget.Toast;

import com.skyking.televant.action_bar.ActionBar;
import com.skyking.televant.basic.BasicActivity;
import com.skyking.televant.navigation.NavigationObject;
import com.skyking.televant.sliding_layout.sample.SampleSlidingFragment;

public class MainActivity extends BasicActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        replaceFragment(R.id.container, new SampleSlidingFragment());

    }
  public void replaceFragment(int containerId, BasicFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, fragment);
        ft.commit();
    }

}
