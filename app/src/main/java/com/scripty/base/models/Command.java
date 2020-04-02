package com.scripty.base.models;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.scripty.base.R;
import com.scripty.base.ui.views.CommandLayout;

public class Command {

    private CommandType mID;

    private int mX;
    private int mY;
    private int mFromX;
    private int mFromY;
    private int mDuration;
    private int mSpeed;
    private String mHwBtn; // thats temp

    private CommandLayout mThisView;

    public enum CommandType {
        TOUCH,
        SLEEP,
        HARDWARE_BUTTON,
        SWIPE,
        TOUCH_AND_HOLD
    }

    public Command(){   }

    // getters setters
    public void setCommand(CommandType id){
        mID = id;
    }

    public CommandType getCommand(){
        return this.mID;
    }

    // layout
    public void setLayout(CommandLayout v){
        mThisView = v;
    }

    public CommandLayout getLayout(){
        return mThisView;
    }



}
