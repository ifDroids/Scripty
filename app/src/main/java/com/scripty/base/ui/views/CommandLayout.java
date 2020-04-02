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
import androidx.core.content.ContextCompat;

import com.scripty.base.R;

public class CommandLayout extends LinearLayout {

    private Activity mActivity;
    private View mThisView;

    public CommandLayout(Activity act){
        super(act,null);
        mActivity=act;

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.command_preference,null);
        mThisView = view;
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
        mThisView = view;
    }

    public void migrateIcon(int id){
         ImageView img =  mThisView.findViewById(R.id.command_action_icon);
         img.setImageDrawable(ContextCompat.getDrawable(mActivity,id));
    }

    public void handleTextViews(int command){
        switch (command){
            case 0:
            case 2:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                break;
            case 1:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                break;
            case 3:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.VISIBLE);
                break;
            case 4:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                break;
        }
    }


    // default constructor
    public CommandLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
