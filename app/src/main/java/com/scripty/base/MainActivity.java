package com.scripty.base;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.scripty.base.libs.BaseFragmentSaveView.wrappers.BaseActivityFragmentLoader;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivityFragmentLoader {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (!RootUtils.isDeviceRooted()){
//            Toast.makeText(this,"This application needs root access",Toast.LENGTH_LONG).show();
//            finish();
//            moveTaskToBack(true);
//            System.exit(-1);
//            return;
//        } else {
//            Log.e("MainActivity","Its rooted.");
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_build, R.id.nav_settings, R.id.nav_help)
                .setDrawerLayout(mDrawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mNavView, navController);


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
