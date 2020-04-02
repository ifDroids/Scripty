package com.scripty.base.models;

public class Command {

    private CommandType mID;

    private int mX;
    private int mY;
    private int mFromX;
    private int mFromY;
    private int mDuration;
    private int mSpeed;
    private String mHwBtn; // thats temp

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
}
