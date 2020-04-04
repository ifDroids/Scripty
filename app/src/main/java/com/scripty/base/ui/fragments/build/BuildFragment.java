package com.scripty.base.ui.fragments.build;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scripty.base.CommandExecutor;
import com.scripty.base.R;
import com.scripty.base.libs.BaseFragmentSaveView.wrappers.BaseFragmentSaveView;
import com.scripty.base.models.Command;
import com.scripty.base.ui.views.CommandLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildFragment extends BaseFragmentSaveView {

    @BindView(R.id.command_add)
    FloatingActionButton mCommandAdd;
    @BindView(R.id.commands_run)
    FloatingActionButton mCommandRunner;

    private Context mContext;

    private Command curCommand;

    private EditText edit11;
    private EditText edit12;
    private EditText edit21;
    private EditText edit22;
    private EditText edit23;

    private int mScreenWidth;
    private int mScreenHeight;

    private static List<Command> allCommands = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View root = onCreateSavedView(inflater, container, savedInstanceState, R.layout.fragment_build);

        mContext = getContext();
        if (mContext == null) {
            Log.e("BuildFragment", "Could not get context.Exiting...");
            System.exit(0);
        }

        // Display coords
        getDisplaySize();

        final LinearLayout commandsContainer = root.findViewById(R.id.commands_container);

        ButterKnife.bind(this, root);

        mCommandAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                Activity act = getActivity();
                if (act != null) {
                    @SuppressLint("InflateParams") final View customLayout = getActivity().getLayoutInflater().inflate(R.layout.command_add_dialog, null);
                    builder.setView(customLayout);

                    Button btnAdd = customLayout.findViewById(R.id.add);
                    Button btnCancel = customLayout.findViewById(R.id.cancel);

                    // create new command object
                    curCommand = new Command();

                    // implement listeners
                    edit11 = customLayout.findViewById(R.id.edit_11);
                    edit12 = customLayout.findViewById(R.id.edit_12);
                    edit21 = customLayout.findViewById(R.id.edit_21);
                    edit22 = customLayout.findViewById(R.id.edit_22);
                    edit23 = customLayout.findViewById(R.id.edit_23);

                    initDialogListeners(customLayout);

                    final AlertDialog dialog = builder.create();
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });

                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            switch (curCommand.getCommand()) {
                                case TOUCH:
                                    if (edit11.getText().length() == 0 || edit12.getText().length() == 0) {
                                        Toast.makeText(mContext, "Input fields cant be empty.", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (isTargetWidthNotValid(edit11) || isTargetHeightNotValid(edit12)){
                                        Toast.makeText(mContext, "Target coordinates are bigger than screen size.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    curCommand.setParams("", Integer.parseInt(edit11.getText().toString()), Integer.parseInt(edit12.getText().toString()));
                                    break;
                                case SLEEP:
                                    if (edit11.getText().length() == 0) {
                                        Toast.makeText(mContext, "Input fields cant be empty.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    curCommand.setParams("", Integer.parseInt(edit11.getText().toString()));
                                    break;
                                case HARDWARE_BUTTON:
                                    if (edit12.getText().length() == 0) {
                                        Toast.makeText(mContext, "Input fields cant be empty.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    curCommand.setParams(edit11.getText().toString(), Integer.parseInt(edit12.getText().toString()));
                                    break;
                                case SWIPE:
                                    if (edit11.getText().length() == 0 || edit12.getText().length() == 0 || edit21.getText().length() == 0 || edit22.getText().length() == 0 || edit23.getText().length() == 0) {
                                        Toast.makeText(mContext, "Input fields cant be empty.", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (isTargetWidthNotValid(edit11) || isTargetHeightNotValid(edit12) || isTargetWidthNotValid(edit21) || isTargetHeightNotValid(edit22)){
                                        Toast.makeText(mContext, "Target coordinates are bigger than screen size.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    curCommand.setParams("",
                                            Integer.parseInt(edit11.getText().toString()),
                                            Integer.parseInt(edit12.getText().toString()),
                                            Integer.parseInt(edit21.getText().toString()),
                                            Integer.parseInt(edit22.getText().toString()),
                                            Integer.parseInt(edit23.getText().toString()));
                                    break;
                                case TOUCH_AND_HOLD:
                                    if (edit21.getText().length() == 0 || edit22.getText().length() == 0 || edit23.getText().length() == 0) {
                                        Toast.makeText(mContext, "Input fields cant be empty.", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (isTargetWidthNotValid(edit21) || isTargetHeightNotValid(edit22)){
                                        Toast.makeText(mContext, "Target coordinates are bigger than screen size.", Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    curCommand.setParams("",
                                            Integer.parseInt(edit21.getText().toString()),
                                            Integer.parseInt(edit22.getText().toString()),
                                            Integer.parseInt(edit23.getText().toString()));
                                    break;
                            }
                            // append the commandLayout
                            final CommandLayout commandLayout = new CommandLayout(getActivity(), curCommand);

                            // click listener for delete
                            final ImageView deleteCommand = commandLayout.findViewById(R.id.delete_command);
                            deleteCommand.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    commandsContainer.removeView(commandLayout);
                                }
                            });


                            // add it to our list
                            allCommands.add(curCommand);

                            // append it to parent
                            commandsContainer.removeView(commandLayout);
                            commandsContainer.addView(commandLayout);

                            // save the fragment
                            saveCurrentViewState(root);

                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                    dialog.show();
                }
            }
        });


        mCommandRunner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //send this app to background
                Intent i = new Intent();
                i.setAction(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                mContext.startActivity(i);

                Log.e("BuildFragment","Commands size :"+allCommands.size()+"");
                Intent serviceIntent = new Intent(mContext, CommandExecutor.class);
                serviceIntent.putExtra("commandsList", (Serializable) allCommands);
                ContextCompat.startForegroundService(mContext, serviceIntent);

            }
        });

        return root;
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SetTextI18n")
    private void initDialogListeners(final View dialogView) {

        final ImageView commandTouch = dialogView.findViewById(R.id.command_touch);
        final ImageView commandSleep = dialogView.findViewById(R.id.command_sleep);
        final ImageView commandHardwareKey = dialogView.findViewById(R.id.command_hardware);
        final ImageView commandSwipe = dialogView.findViewById(R.id.command_swipe);
        final ImageView commandTouchHold = dialogView.findViewById(R.id.command_touchhold);


        final TextView tvCommand = dialogView.findViewById(R.id.parameters_and_command);
        final String params = tvCommand.getText() + "";

        // init
        {
            tvCommand.setText(params + " (Single Touch)");
            commandTouch.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
            curCommand.setCommand(Command.CommandType.TOUCH);
            edit11.setInputType(InputType.TYPE_CLASS_NUMBER);
            edit12.setInputType(InputType.TYPE_CLASS_NUMBER);

            // show the first row of edittexts
            edit11.setVisibility(View.VISIBLE);
            edit12.setVisibility(View.VISIBLE);
            // hide second row of edittexts
            edit21.setVisibility(View.GONE);
            edit22.setVisibility(View.GONE);
            edit23.setVisibility(View.GONE);
            // add the corresponding text hint
            edit11.setHint("X: ");
            edit12.setHint("Y: ");
        }

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // clean up all
                // reset weights,gravity and visibility
                initDialog(dialogView);

                // enable the first tv (doesnt work on last case: . we do it there programmatically)
                edit11.requestFocus();

                switch (v.getId()) {
                    case R.id.command_touch:
                        tvCommand.setText(params + " (Single Touch)");
                        commandTouch.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
                        // show the first row of edittexts
                        edit11.setVisibility(View.VISIBLE);
                        edit12.setVisibility(View.VISIBLE);
                        // add the corresponding text hint
                        edit11.setHint("X: ");
                        edit12.setHint("Y: ");
                        // update inputtype
                        edit11.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit12.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.TOUCH);
                        break;
                    case R.id.command_sleep:
                        tvCommand.setText(params + " (Sleep)");
                        commandSleep.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));

                        edit11.setVisibility(View.VISIBLE);
                        // add the corresponding text hint
                        edit11.setHint("Duration (ms):");
                        edit11.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.SLEEP);
                        break;
                    case R.id.command_hardware:
                        tvCommand.setText(params + " (Hardware Button)");
                        commandHardwareKey.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));

                        // show the first row of edittexts
                        edit11.setVisibility(View.VISIBLE);
                        edit12.setVisibility(View.VISIBLE);

                        // add the corresponding text hint
                        edit11.setHint("Button:");
                        edit12.setHint("Hold (ms):");
                        edit11.setInputType(InputType.TYPE_CLASS_TEXT);
                        edit12.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.HARDWARE_BUTTON);
                        break;
                    case R.id.command_swipe:
                        tvCommand.setText(params + " (Swipe)");
                        commandSwipe.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
                        // hide nothing
                        edit21.setVisibility(View.VISIBLE);
                        edit22.setVisibility(View.VISIBLE);
                        edit23.setVisibility(View.VISIBLE);
                        edit11.setVisibility(View.VISIBLE);
                        edit12.setVisibility(View.VISIBLE);

                        edit11.setHint("fromX:");
                        edit12.setHint("fromY:");
                        edit21.setHint("toX:");
                        edit22.setHint("toY:");
                        edit23.setHint("Speed (ms):");
                        edit11.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit12.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit21.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit22.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit23.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.SWIPE);
                        break;
                    case R.id.command_touchhold:
                        tvCommand.setText(params + " (Touch and hold)");
                        commandTouchHold.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));

                        edit21.setVisibility(View.VISIBLE);
                        edit22.setVisibility(View.VISIBLE);
                        edit23.setVisibility(View.VISIBLE);

                        edit21.setHint("X:");
                        edit22.setHint("Y:");
                        edit23.setHint("Hold (ms):");
                        edit21.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit22.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit23.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.TOUCH_AND_HOLD);

                        // enable the first tv
                        edit21.requestFocus();
                        break;
                }
            }
        };

        commandTouch.setOnClickListener(listener);
        commandSleep.setOnClickListener(listener);
        commandHardwareKey.setOnClickListener(listener);
        commandSwipe.setOnClickListener(listener);
        commandTouchHold.setOnClickListener(listener);

    }

    private void initDialog(View v) {
        final LinearLayout commandsIconsContainer = v.findViewById(R.id.commands_icons_container);
        final LinearLayout paramsFirst = v.findViewById(R.id.params_first_line);
        final LinearLayout paramsSecond = v.findViewById(R.id.params_second_line);

        for (int i = 0; i < commandsIconsContainer.getChildCount(); i++)
            if (commandsIconsContainer.getChildAt(i) instanceof ImageView)
                ((ImageView) commandsIconsContainer.getChildAt(i)).setColorFilter(null);

        for (int i = 0; i < paramsFirst.getChildCount(); i++)
            if (paramsFirst.getChildAt(i) instanceof EditText) {
                ((EditText) paramsFirst.getChildAt(i)).setText("");
                paramsFirst.getChildAt(i).setVisibility(View.GONE);
            }
        for (int i = 0; i < paramsSecond.getChildCount(); i++)
            if (paramsSecond.getChildAt(i) instanceof EditText) {
                ((EditText) paramsSecond.getChildAt(i)).setText("");
                paramsSecond.getChildAt(i).setVisibility(View.GONE);
            }
    }

    private void getDisplaySize() {
            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            mScreenWidth = size.x;
            mScreenHeight = size.y;
    }

    private boolean isTargetWidthNotValid(EditText T) {
        return Integer.parseInt(T.getText().toString()) > mScreenWidth;
    }

    private boolean isTargetHeightNotValid(EditText T) {
        return Integer.parseInt(T.getText().toString()) > mScreenHeight;
    }
}





































