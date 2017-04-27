package studio.dexter.basic;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import studio.dexter.ohmoneytrial.NavigationDrawerFragment;
import studio.dexter.ohmoneytrial.R;
import studio.dexter.system.FragmentMas;
import studio.dexter.system.FragmentMaster;


/**
 * Created by dexter on 2015/6/14.
 */
public abstract class BasicActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    protected FragmentMaster fragmentMaster;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;
    protected CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentMaster = new FragmentMas(this);
        initSystem();
        setContentView(R.layout.activity_main);
//        navigationTitles = getResources().getStringArray(R.array.navigation_title_array);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    /**
     * init system before setContantView method.
     */
    protected abstract void initSystem();


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        BasicFragment fragment = fragmentMaster.getFragmentWithNavigation(position);
        fragmentMaster.replaceFragment(fragment, true);
    }


    public void replaceFragment(BasicFragment fragment, boolean clearHistory) {
        fragmentMaster.replaceFragment(fragment, clearHistory);
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    public void onSectionAttached(int number) {
        mTitle = getResources().getStringArray(R.array.navigation_title_array)[number];
    }

    @Override
    public void onBackPressed() {
        fragmentMaster.back();
    }
	
	 View decorView;
    final int HIDE_NAVIGATION_BAR_FLAG = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    public void hideNavigationBar() {

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion < Build.VERSION_CODES.KITKAT) return;
        if (decorView == null) decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(HIDE_NAVIGATION_BAR_FLAG);
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(HIDE_NAVIGATION_BAR_FLAG);
                        } else {
                        }
                    }
                });
    }

}
