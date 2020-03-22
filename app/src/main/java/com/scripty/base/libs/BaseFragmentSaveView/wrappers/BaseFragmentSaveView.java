package com.scripty.base.libs.BaseFragmentSaveView.wrappers;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scripty.base.libs.BaseFragmentSaveView.interfaces.OnFragmentViewLoadListener;
import com.scripty.base.libs.BaseFragmentSaveView.interfaces.OnFragmentViewSaveListener;


public class BaseFragmentSaveView extends Fragment {

    private OnFragmentViewLoadListener viewLoaderListener;
    private OnFragmentViewSaveListener viewSaveListener;
    private int currentLayoutID;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewSaveListener = (OnFragmentViewSaveListener) context;
        viewLoaderListener = (OnFragmentViewLoadListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = viewLoaderListener.onFragmentViewLoadNow();
        View stockLayout = inflater.inflate(currentLayoutID, container, false);

        if (stockLayout == null) throw new NullPointerException("Could not find fragment layout.Did you call setLayoutID() before super.onCreateView() at your fragment?");

        return v == null ? stockLayout : v ;
    }

    protected void setLayoutID(@LayoutRes int id){
        currentLayoutID = id;
    }

    protected void saveCurrentViewState(View v){

        //here, we can make custom edits like :
        // * Clean up a textbox or something
        viewSaveListener.onFragmentViewSaveNow(v);
    }

}
