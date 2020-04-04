package com.scripty.base.ui.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
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

        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

        TextView tv1 = mThisView.findViewById(R.id.tv1);
        TextView tv2 = mThisView.findViewById(R.id.tv2);
        TextView tv3 = mThisView.findViewById(R.id.tv3);
        TextView tv4 = mThisView.findViewById(R.id.tv4);
        TextView tv5 = mThisView.findViewById(R.id.tv5);

        LinearLayout commandsLinear = mThisView.findViewById(R.id.commands);
        float container_ParamsTotalWeight = commandsLinear.getWeightSum();

        // reset weights
        LayoutParams paramsTV = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.6f );
        tv1.setLayoutParams(paramsTV);
        tv2.setLayoutParams(paramsTV);
        tv3.setLayoutParams(paramsTV);
        tv4.setLayoutParams(paramsTV);
        tv5.setLayoutParams(paramsTV);

        switch (mCommand.getCommand()){
            case TOUCH:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.GONE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("X: "+mCommand.getX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Y: "+mCommand.getY());
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_touch));

                // container_ParamsTotalWeight/2.0f = "the params weight / params quantity"
                paramsTV = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, (container_ParamsTotalWeight/2.0f));
                paramsTV.gravity = Gravity.CENTER;
                tv1.setGravity(Gravity.CENTER);
                tv2.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(paramsTV);
                tv2.setLayoutParams(paramsTV);
                break;
            case HARDWARE_BUTTON:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.GONE);

                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("Button: "+mCommand.getHwButton());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Duration: "+mCommand.getDuration()+ " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_hwkey));

                // container_ParamsTotalWeight/2.0f = "the params weight / params quantity"
                paramsTV = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, (container_ParamsTotalWeight/2.0f));
                paramsTV.gravity = Gravity.CENTER;
                tv1.setGravity(Gravity.CENTER);
                tv2.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(paramsTV);
                tv2.setLayoutParams(paramsTV);
                break;
            case SLEEP:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.GONE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("Duration: "+mCommand.getDuration() + " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_sleep));

                // container_ParamsTotalWeight/2.0f = "the params weight / params quantity"
                paramsTV = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, container_ParamsTotalWeight );
                paramsTV.gravity = Gravity.CENTER;
                tv1.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(paramsTV);
                break;
            case SWIPE:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.VISIBLE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("fromX:"+mCommand.getFromX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("fromY:"+mCommand.getFromY() );
                ( (TextView) mThisView.findViewById(R.id.tv3)).setText("toX:"+mCommand.getX());
                ( (TextView) mThisView.findViewById(R.id.tv4)).setText("toY:"+mCommand.getY());
                ( (TextView) mThisView.findViewById(R.id.tv5)).setText("Speed:"+mCommand.getSpeed()+ " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_swipe));

                // container_ParamsTotalWeight/2.0f = "the params weight / params quantity"
                paramsTV = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, (container_ParamsTotalWeight/5.0f));
                paramsTV.gravity = Gravity.CENTER;
                tv1.setGravity(Gravity.CENTER);
                tv2.setGravity(Gravity.CENTER);
                tv3.setGravity(Gravity.CENTER);
                tv4.setGravity(Gravity.CENTER);
                tv5.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(paramsTV);
                tv2.setLayoutParams(paramsTV);
                tv3.setLayoutParams(paramsTV);
                tv4.setLayoutParams(paramsTV);
                tv5.setLayoutParams(paramsTV);
                break;
            case TOUCH_AND_HOLD:
                mThisView.findViewById(R.id.tv1).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv2).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv3).setVisibility(View.VISIBLE);
                mThisView.findViewById(R.id.tv4).setVisibility(View.GONE);
                mThisView.findViewById(R.id.tv5).setVisibility(View.GONE);
                ( (TextView) mThisView.findViewById(R.id.tv1)).setText("X: "+mCommand.getX());
                ( (TextView) mThisView.findViewById(R.id.tv2)).setText("Y: "+mCommand.getY() );
                ( (TextView) mThisView.findViewById(R.id.tv3)).setText("Duration: "+mCommand.getX()+ " ms");
                img.setImageDrawable(ContextCompat.getDrawable(mActivity,R.drawable.ic_command_taphold));

                // container_ParamsTotalWeight/2.0f = "the params weight / params quantity"
                paramsTV = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, (container_ParamsTotalWeight/3.0f));
                paramsTV.gravity = Gravity.CENTER;
                tv1.setGravity(Gravity.CENTER);
                tv2.setGravity(Gravity.CENTER);
                tv3.setGravity(Gravity.CENTER);
                tv1.setLayoutParams(paramsTV);
                tv2.setLayoutParams(paramsTV);
                tv3.setLayoutParams(paramsTV);
                break;
        }
    }


    // default constructor
    public CommandLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
