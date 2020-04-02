package com.scripty.base.ui.fragments.build;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
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
import com.scripty.base.R;
import com.scripty.base.libs.BaseFragmentSaveView.wrappers.BaseFragmentSaveView;
import com.scripty.base.models.Command;
import com.scripty.base.ui.views.CommandLayout;

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

    private static List<Command> allCommands = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final View root = onCreateSavedView(inflater, container, savedInstanceState, R.layout.fragment_build);

        mContext = getContext();
        if (mContext == null) {
            Log.e("BuildFragment", "Could not get context.Exiting...");
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
                            // append the commandLayout
                            final CommandLayout commandLayout = new CommandLayout(getActivity());
                            switch (curCommand.getCommand()) {
                                case TOUCH:
                                    if (edit11.getText().length() == 0 || edit12.getText().length() == 0 ){
                                        Toast.makeText(mContext,"Input fields cant be empty.",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (edit11.getText().length() > 4 || edit12.getText().length() > 4 ){ // pixel coords cant be higher than 4 digits
                                        Toast.makeText(mContext,"Dimension coordinates cant be 5-digit numbers",Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    commandLayout.migrateIcon(R.drawable.ic_command_touch);
                                    curCommand.setParams("", Integer.parseInt(edit11.getText().toString()), Integer.parseInt(edit12.getText().toString()));

                                    break;
                                case SLEEP:
                                    if (edit11.getText().length() == 0  ){
                                        Toast.makeText(mContext,"Input fields cant be empty.",Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    commandLayout.migrateIcon(R.drawable.ic_command_sleep);
                                    curCommand.setParams("", Integer.parseInt(edit11.getText().toString()));
                                    break;
                                case HARDWARE_BUTTON:
                                    if (edit12.getText().length() == 0  ){
                                        Toast.makeText(mContext,"Input fields cant be empty.",Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    commandLayout.migrateIcon(R.drawable.ic_command_hwkey);
                                    curCommand.setParams(edit11.getText().toString(), Integer.parseInt(edit12.getText().toString()));
                                    break;
                                case SWIPE:
                                    if (edit11.getText().length() == 0  || edit12.getText().length() == 0 || edit21.getText().length() == 0 || edit22.getText().length() == 0 || edit23.getText().length() == 0){
                                        Toast.makeText(mContext,"Input fields cant be empty.",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (edit11.getText().length()  > 4  || edit12.getText().length()  > 4 || edit21.getText().length()  > 4 || edit22.getText().length()  > 4 ){
                                        Toast.makeText(mContext,"Dimension coordinates cant be 5-digit numbers",Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    commandLayout.migrateIcon(R.drawable.ic_command_swipe);
                                    curCommand.setParams("",
                                            Integer.parseInt(edit11.getText().toString()),
                                            Integer.parseInt(edit12.getText().toString()),
                                            Integer.parseInt(edit21.getText().toString()),
                                            Integer.parseInt(edit22.getText().toString()),
                                            Integer.parseInt(edit23.getText().toString()));
                                    break;
                                case TOUCH_AND_HOLD:
                                    if (  edit21.getText().length() == 0 || edit22.getText().length() == 0 || edit23.getText().length() == 0){
                                        Toast.makeText(mContext,"Input fields cant be empty.",Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    if (  edit21.getText().length() > 4 || edit22.getText().length() > 4 ){
                                        Toast.makeText(mContext,"Dimension coordinates cant be 5-digit numbers",Toast.LENGTH_LONG).show();
                                        return;
                                    }

                                    commandLayout.migrateIcon(R.drawable.ic_command_taphold);
                                    curCommand.setParams("",
                                            Integer.parseInt(edit21.getText().toString()),
                                            Integer.parseInt(edit22.getText().toString()),
                                            Integer.parseInt(edit23.getText().toString()));
                                    break;
                            }
                            curCommand.setLayout(commandLayout);
                            commandLayout.handleTextViews(curCommand);
                            allCommands.add(curCommand);


                            // Finally add the rest UI stuff....

                            // click listener for delete
                            final ImageView deleteCommand = commandLayout.findViewById(R.id.delete_command);
                            deleteCommand.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    commandsContainer.removeView(commandLayout);
                                }
                            });

                            commandsContainer.removeView(commandLayout);
                            commandsContainer.addView(commandLayout);

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
                Log.e("BuildFragment",allCommands.size()+"");
                int x=0;
                for(Command c : allCommands){
                    Log.e("BuildFragment",c.toString() + " index:" +x);
                    x++;
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


        final TextView tvCommand = v.findViewById(R.id.parameters_and_command);
        final String params = tvCommand.getText() + "";

        // init
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

        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // clean up all
                commandTouch.setColorFilter(null);
                commandSleep.setColorFilter(null);
                commandHardwareKey.setColorFilter(null);
                commandSwipe.setColorFilter(null);
                commandTouchHold.setColorFilter(null);
                edit11.setText("");
                edit12.setText("");
                edit21.setText("");
                edit22.setText("");
                edit23.setText("");

                switch (v.getId()) {
                    case R.id.command_touch:
                        tvCommand.setText(params + " (Single Touch)");
                        commandTouch.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
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
                        // update inputtype
                        edit11.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edit12.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.TOUCH);
                         break;
                    case R.id.command_sleep:
                        tvCommand.setText(params + " (Sleep)");
                        commandSleep.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));

                        // show the first row of edittexts
                        edit11.setVisibility(View.VISIBLE);
                        // hide second row of edittexts
                        edit21.setVisibility(View.GONE);
                        edit22.setVisibility(View.GONE);
                        edit23.setVisibility(View.GONE);
                        edit12.setVisibility(View.GONE);
                        // add the corresponding text hint
                        edit11.setHint("Duration (ms): ");
                        edit11.setInputType(InputType.TYPE_CLASS_NUMBER);

                        curCommand.setCommand(Command.CommandType.SLEEP);

                        break;
                    case R.id.command_hardware:
                        tvCommand.setText(params + " (Hardware Button)");
                        commandHardwareKey.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));

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

                        edit11.setHint("fromX: ");
                        edit12.setHint("fromY: ");
                        edit21.setHint("toX: ");
                        edit22.setHint("toY: ");
                        edit23.setHint("Speed (ms): ");
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

                        //hide first row
                        edit11.setVisibility(View.GONE);
                        edit12.setVisibility(View.GONE);
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





































