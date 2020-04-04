package com.ifdroids.scripty.libs.BaseFragmentSaveView.wrappers;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ifdroids.scripty.libs.BaseFragmentSaveView.interfaces.OnFragmentViewLoadListener;
import com.ifdroids.scripty.libs.BaseFragmentSaveView.interfaces.OnFragmentViewSaveListener;


@SuppressLint("Registered") // This activity is just a wrapper. No need to be registered to Manifest
public class BaseActivityFragmentLoader extends AppCompatActivity implements OnFragmentViewLoadListener, OnFragmentViewSaveListener {

    private View fragmentLayout;

    @Override
    public void onFragmentViewSaveNow(View data) {
        fragmentLayout = data;
    }

    @Override
    public View onFragmentViewLoadNow() {
        return fragmentLayout;
    }
}
