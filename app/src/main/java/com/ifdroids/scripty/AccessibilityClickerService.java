package com.ifdroids.scripty;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.RequiresApi;

public class AccessibilityClickerService extends AccessibilityService {

    public static AccessibilityClickerService instance;

    @Override
    protected void onServiceConnected() {
        instance=this;

    }

    public void sendFuckingClick(float x,float y){
        dispatchGesture(createClick(400, 600), callback, null);
    }

    public void stop(){
        this.stopSelf();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }


    @Override
    public void onInterrupt() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static GestureDescription createClick(float x, float y) {
        // for a single tap a duration of 1 ms is enough
        final int DURATION = 1;

        Path clickPath = new Path();
        clickPath.moveTo(x, y);
        GestureDescription.StrokeDescription clickStroke =
                new GestureDescription.StrokeDescription(clickPath, 0, DURATION);
        GestureDescription.Builder clickBuilder = new GestureDescription.Builder();
        clickBuilder.addStroke(clickStroke);
        return clickBuilder.build();
    }

    // callback invoked either when the gesture has been completed or cancelled
    AccessibilityService.GestureResultCallback callback = new AccessibilityService.GestureResultCallback() {
        @Override
        public void onCompleted(GestureDescription gestureDescription) {
            super.onCompleted(gestureDescription);
            Log.e("AccessibilityClickerService", "Gesture Send.");
        }

        @Override
        public void onCancelled(GestureDescription gestureDescription) {
            super.onCancelled(gestureDescription);
            Log.e("AccessibilityClickerService", "Gesture cancelled.");
        }
    };
}