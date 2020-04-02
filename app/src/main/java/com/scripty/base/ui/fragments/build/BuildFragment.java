package com.scripty.base.ui.fragments.build;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scripty.base.R;
import com.scripty.base.libs.BaseFragmentSaveView.wrappers.BaseFragmentSaveView;
import com.scripty.base.ui.views.CommandLayout;


import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildFragment extends BaseFragmentSaveView {

    @BindView(R.id.command_add)
    FloatingActionButton mCommandAdd;
    private Context mContext;
    private int selectedCommand = 0 ;//0 is the first command,1 is the second etc...

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View root = onCreateSavedView(inflater, container, savedInstanceState, R.layout.fragment_home);

        mContext = getContext();
        if (mContext == null ){
            Log.e("BuildFragment","Could not get context.Exiting...");
            System.exit(0);
        }

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

                    // implement listeners
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
                            // append the command
                            final CommandLayout command = new CommandLayout(getActivity());
                            switch (selectedCommand){
                                case 0:
                                    command.migrateIcon(R.drawable.ic_command_touch);

                                    break;
                                case 1:
                                    command.migrateIcon(R.drawable.ic_command_sleep);
                                    break;
                                case 2:
                                    command.migrateIcon(R.drawable.ic_command_hwkey);
                                    break;
                                case 3:
                                    command.migrateIcon(R.drawable.ic_command_swipe);
                                    break;
                                case 4:
                                    command.migrateIcon(R.drawable.ic_command_taphold);
                                    break;
                            }
                            command.handleTextViews(selectedCommand);

                            // click listener for delete
                            final ImageView deleteCommand = command.findViewById(R.id.delete_command);
                            deleteCommand.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("dsdsds", "patithika");
                                    commandsContainer.removeView(command);
                                }
                            });

                            commandsContainer.removeView(command);
                            commandsContainer.addView(command);

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

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @SuppressLint("SetTextI18n")
    private void initDialogListeners(View v) {



        final ImageView commandTouch = v.findViewById(R.id.command_touch);
        final ImageView commandSleep = v.findViewById(R.id.command_sleep);
        final ImageView commandHardwareKey = v.findViewById(R.id.command_hardware);
        final ImageView commandSwipe = v.findViewById(R.id.command_swipe);
        final ImageView commandTouchHold = v.findViewById(R.id.command_touchhold);

        final EditText edit11 = v.findViewById(R.id.edit_11);
        final EditText edit12 = v.findViewById(R.id.edit_12);
        final EditText edit21 = v.findViewById(R.id.edit_21);
        final EditText edit22 = v.findViewById(R.id.edit_22);
        final EditText edit23 = v.findViewById(R.id.edit_23);

        final TextView tvCommand = v.findViewById(R.id.parameters_and_command);
        final String params=tvCommand.getText()+"";

        // init
        tvCommand.setText( params + " (Single Touch)"  );
        commandTouch.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));
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

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // clean up all
                commandTouch.setColorFilter(null);
                commandSleep.setColorFilter(null);
                commandHardwareKey.setColorFilter(null);
                commandSwipe.setColorFilter(null);
                commandTouchHold.setColorFilter(null);

                switch (v.getId()) {
                    case R.id.command_touch:
                        tvCommand.setText( params + " (Single Touch)"  );
                        commandTouch.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));
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

                        selectedCommand=0;
                        break;
                    case R.id.command_sleep:
                        tvCommand.setText( params + " (Sleep)"  );
                        commandSleep.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));

                        // show the first row of edittexts
                        edit11.setVisibility(View.VISIBLE);
                        // hide second row of edittexts
                        edit21.setVisibility(View.GONE);
                        edit22.setVisibility(View.GONE);
                        edit23.setVisibility(View.GONE);
                        edit12.setVisibility(View.GONE);
                        // add the corresponding text hint
                        edit11.setHint("Duration (ms): ");
                        selectedCommand=1;
                        break;
                    case R.id.command_hardware:
                        tvCommand.setText( params + " (Hardware Button)"  );
                        commandHardwareKey.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));

                        // show the first row of edittexts
                        edit11.setVisibility(View.VISIBLE);
                        edit12.setVisibility(View.VISIBLE);
                        // hide second row of edittexts
                        edit21.setVisibility(View.GONE);
                        edit22.setVisibility(View.GONE);
                        edit23.setVisibility(View.GONE);

                        // add the corresponding text hint
                        edit11.setHint("Button: ");
                        edit12.setHint("Hold (ms): ");
                        selectedCommand=2;
                        break;
                    case R.id.command_swipe:
                        tvCommand.setText( params + " (Swipe)"  );
                        commandSwipe.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));
                        // hide nothing
                        edit21.setVisibility(View.VISIBLE);
                        edit22.setVisibility(View.VISIBLE);
                        edit23.setVisibility(View.VISIBLE);
                        edit11.setVisibility(View.VISIBLE);
                        edit12.setVisibility(View.VISIBLE);

                        edit11.setHint("fromX: ");
                        edit12.setHint("fromY: ");
                        edit21.setHint("toX: ");
                        edit22.setHint("toY: ");
                        edit23.setHint("Speed (ms): ");
                        selectedCommand=3;
                        break;
                    case R.id.command_touchhold:
                        tvCommand.setText( params + " (Touch and hold)"  );
                        commandTouchHold.setColorFilter(ContextCompat.getColor(mContext,R.color.colorAccent));

                        //hide first row
                        edit11.setVisibility(View.GONE);
                        edit12.setVisibility(View.GONE);
                        edit21.setVisibility(View.VISIBLE);
                        edit22.setVisibility(View.VISIBLE);
                        edit23.setVisibility(View.VISIBLE);

                        edit21.setHint("X:");
                        edit22.setHint("Y:");
                        edit23.setHint("Hold (ms):");
                        selectedCommand=4;
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
}





































