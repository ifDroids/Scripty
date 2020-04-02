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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.scripty.base.R;
import com.scripty.base.models.Command;

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

    @SuppressLint("SetTextI18n")
    public void handleTextViews(Command command){
        switch (command.getCommand()){
            case TOUCH:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("X: "+command.getX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Y: "+command.getY());

                break;
            case HARDWARE_BUTTON:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);

                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("Button: "+command.getHwButton());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Duration: "+command.getDuration()+ " ms");
                break;
            case SLEEP:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("Duration: "+command.getDuration() + " ms");
                break;
            case SWIPE:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.VISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("fromX:\n"+command.getFromX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("fromY:\n"+command.getFromY() );
                ( (TextView) mThisView.findViewById(R.id.tv3)).setText("toX:\n"+command.getX());
                ( (TextView) mThisView.findViewById(R.id.tv4)).setText("toY:\n"+command.getY());
                ( (TextView) mThisView.findViewById(R.id.tv5)).setText("Speed:\n"+command.getSpeed()+ " ms");
                break;
            case TOUCH_AND_HOLD:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("X: "+command.getX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Y: "+command.getY() );
                ( (TextView) mThisView.findViewById(R.id.tv3)).setText("Duration: "+command.getX()+ " ms");
                break;
        }
    }


    // default constructor
    public CommandLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
