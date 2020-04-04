package com.ifdroids.scripty.libs.BaseFragmentSaveView.wrappers;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ifdroids.scripty.libs.BaseFragmentSaveView.interfaces.OnFragmentViewLoadListener;
import com.ifdroids.scripty.libs.BaseFragmentSaveView.interfaces.OnFragmentViewSaveListener;


public class BaseFragmentSaveView extends Fragment {

    private OnFragmentViewLoadListener viewLoaderListener;
    private OnFragmentViewSaveListener viewSaveListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewSaveListener = (OnFragmentViewSaveListener) context;
        viewLoaderListener = (OnFragmentViewLoadListener) context;
    }

    protected void saveCurrentViewState(View v){

        //here, we can make custom edits like :
        // * Clean up a textbox or something
        viewSaveListener.onFragmentViewSaveNow(v);
    }

    protected View onCreateSavedView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, @LayoutRes int layoutID) {
        View v = viewLoaderListener.onFragmentViewLoadNow();
        View stockLayout = inflater.inflate(layoutID, container, false);

        if (stockLayout == null) throw new NullPointerException("Could not find fragment layout.Did you call setLayoutID() before super.onCreateView() at your fragment?");

        return v == null ? stockLayout : v ;
    }

}
