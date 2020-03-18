package com.scripty.base.ui.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.scripty.base.R;

public class CommandLayout extends LinearLayout {

    private Activity mActivity;

    public CommandLayout(Activity act){
        super(act,null);
        mActivity=act;

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.command_preference,null);

        // dynamically draw it
        LinearLayout container = view.findViewById(R.id.command_container);

        LinearLayout.LayoutParams params = (LayoutParams) container.getLayoutParams();
        params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(0,0,0,20);
        container.setLayoutParams(params);

        // listeners
        ImageView imageDelete = view.findViewById(R.id.delete_command);
        imageDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // will remove the view and the dispose the class instance
            }
        });
        this.addView(view);
    }


    // default constructor
    public CommandLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
