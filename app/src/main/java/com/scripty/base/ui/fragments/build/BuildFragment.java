package com.scripty.base.ui.fragments.build;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scripty.base.R;
import com.scripty.base.libs.BaseFragmentSaveView.wrappers.BaseFragmentSaveView;
import com.scripty.base.ui.views.CommandLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildFragment extends BaseFragmentSaveView {

    @BindView(R.id.command_add)
    FloatingActionButton mCommandAdd;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View root = onCreateSavedView(inflater, container, savedInstanceState, R.layout.fragment_home);

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

                    Button btnOk = customLayout.findViewById(R.id.ok);
                    Button btnCancel = customLayout.findViewById(R.id.cancel);

                    // implement listeners
                    initDialogListeners(customLayout);

                    final AlertDialog dialog = builder.create();
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // append the command
                            final CommandLayout command = new CommandLayout(getActivity());

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

    private void initDialogListeners(View v) {

        final ImageView commandTouch = v.findViewById(R.id.command_touch);
        final ImageView commandSleep = v.findViewById(R.id.command_sleep);
        final ImageView commandHardwareKey = v.findViewById(R.id.command_hardware);
        final ImageView commandSwipe = v.findViewById(R.id.command_swipe);
        final ImageView commandTouchHold = v.findViewById(R.id.command_touchhold);

        final TextView tvCommand = v.findViewById(R.id.parameters_and_command);
        final String params=tvCommand.getText()+"";



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
                        commandTouch.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        break;
                    case R.id.command_sleep:
                        tvCommand.setText( params + " (Sleep)"  );
                        commandSleep.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        break;
                    case R.id.command_hardware:
                        tvCommand.setText( params + " (Hardware Button)"  );
                        commandHardwareKey.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        break;
                    case R.id.command_swipe:
                        tvCommand.setText( params + " (Swipe)"  );
                        commandSwipe.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        break;
                    case R.id.command_touchhold:
                        tvCommand.setText( params + " (Touch and hold)"  );
                        commandTouchHold.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorAccent));
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





































