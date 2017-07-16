package ru.karapetiandav.yamblzproject.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.karapetiandav.yamblzproject.R;
import ru.karapetiandav.yamblzproject.ui.fragments.AboutFragment;
import ru.karapetiandav.yamblzproject.ui.fragments.SettingsFragment;
import ru.karapetiandav.yamblzproject.ui.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private FragmentManager fragmentManager = getFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_weather);
            setTitle(navigationView.getMenu().findItem(R.id.nav_weather).getTitle());
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.container, WeatherFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        if (id == R.id.nav_weather) {
            fragmentClass = WeatherFragment.class;
        } else if (id == R.id.nav_settings) {
            fragmentClass = SettingsFragment.class;
        } else if (id == R.id.nav_about) {
            fragmentClass = AboutFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (InstantiationException e) {
            Log.e(TAG, "onNavigationItemSelected: ", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "onNavigationItemSelected: ", e);
        }

        fragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        item.setChecked(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}