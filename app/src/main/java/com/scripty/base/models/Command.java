package com.scripty.base.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.scripty.base.ui.views.CommandLayout;

import java.io.Serializable;

public class Command implements Serializable {

    private CommandType mCommand;

    private int mX = -1;
    private int mY = -1;
    private int mFromX = -1;
    private int mFromY = -1;
    private int mDuration = -1;
    private int mSpeed = -1;
    private String mHwBtn = ""; // thats temp

    private CommandLayout mThisView;

    public enum CommandType {
        TOUCH,
        SLEEP,
        HARDWARE_BUTTON,
        SWIPE,
        TOUCH_AND_HOLD
    }

    public Command() {
    }

    // getters setters
    public void setCommand(CommandType id) {
        mCommand = id;
    }

    public CommandType getCommand() {
        return this.mCommand;
    }

    // layout
    public void setLayout(CommandLayout v) {
        mThisView = v;
    }

    public CommandLayout getLayout() {
        return mThisView;
    }

    // params
    public void setParams(@NonNull String hw, @NonNull int... args) {
        // reset em all
        mX = -1;
        mY = -1;
        mFromX = -1;
        mFromY = -1;
        mDuration = -1;
        mSpeed = -1;
        mHwBtn = "";

        // truly update
        mHwBtn = hw;

        Log.e("dsds", mCommand.toString());
        switch (mCommand) {
            case SLEEP:
                mDuration = args[0];
                break;
            case HARDWARE_BUTTON:
                mHwBtn=hw;
                mDuration=args[0];
                break;
            case TOUCH:
                mX = args[0];
                mY = args[1];
                break;
            case TOUCH_AND_HOLD:
                mX = args[0];
                mY = args[1];
                mDuration=args[2];
                break;
            case SWIPE:
                mX = args[0];
                mY = args[1];
                mFromX = args[2];
                mFromY = args[3];
                mSpeed = args[4];
                break;
        }

    }

    public int getX() {return mX;}
    public int getY() {return mY;}
    public int getFromX() {return mFromX;}
    public int getFromY() {return mFromY;}
    public int getDuration() {return mDuration;}
    public int getSpeed() {return mSpeed;}
    public String getHwButton() { return mHwBtn;}






}
