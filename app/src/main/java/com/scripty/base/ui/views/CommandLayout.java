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
    private Command mCommand;

    public CommandLayout(Activity act, Command command){
        super(act,null);
        mActivity=act;
        mCommand = command;

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
        mThisView = view;

        save();
    }



    @SuppressLint("SetTextI18n")
    private void save(){
        ImageView img =  mThisView.findViewById(R.id.command_action_icon);

        switch (mCommand.getCommand()){
            case TOUCH:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("X: "+mCommand.getX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Y: "+mCommand.getY());
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_touch));
                break;
            case HARDWARE_BUTTON:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);

                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("Button: "+mCommand.getHwButton());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Duration: "+mCommand.getDuration()+ " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_hwkey));
                break;
            case SLEEP:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("Duration: "+mCommand.getDuration() + " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_sleep));
                break;
            case SWIPE:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.VISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("fromX:\n"+mCommand.getFromX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("fromY:\n"+mCommand.getFromY() );
                ( (TextView) mThisView.findViewById(R.id.tv3)).setText("toX:\n"+mCommand.getX());
                ( (TextView) mThisView.findViewById(R.id.tv4)).setText("toY:\n"+mCommand.getY());
                ( (TextView) mThisView.findViewById(R.id.tv5)).setText("Speed:\n"+mCommand.getSpeed()+ " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_swipe));
                break;
            case TOUCH_AND_HOLD:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.INVISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.INVISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("X: "+mCommand.getX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Y: "+mCommand.getY() );
                ( (TextView) mThisView.findViewById(R.id.tv3)).setText("Duration: "+mCommand.getX()+ " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_taphold));
                break;
        }
    }


    // default constructor
    public CommandLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
