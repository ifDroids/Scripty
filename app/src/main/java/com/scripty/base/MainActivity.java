package com.scripty.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;

    private AppBarConfiguration mAppBarConfiguration;

    private LiveData currentNavController;

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        setupSidebar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("dsdsds","222222222");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        Log.e("dsdsds","11111111");
        if(savedInstanceState==null)
            setupSidebar();

    }

    private void setupSidebar(){
        CustomNavigationView navView = findViewById(R.id.nav_view);
        List<Integer> navGraphIds = new ArrayList<>();
        navGraphIds.add(R.navigation.home);
        navGraphIds.add(R.navigation.settings);
        navGraphIds.add(R.navigation.help);

        LiveData lifecycler = navView.setupWithNavController(
                navGraphIds,getSupportFragmentManager(),R.id.nav_host_fragment,getIntent());


        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        final AppCompatActivity m = this;
        lifecycler.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                NavigationUI.setupActionBarWithNavController(m,navController);
            }
        });
        currentNavController = lifecycler;
    }
    @Override
    public boolean onSupportNavigateUp() {
        LiveData x = currentNavController;
        if (x != null){
            NavController z = (NavController) x.getValue();
            if(z!=null){
                return z.navigateUp();
            }
        }
        return false;
    }
}
