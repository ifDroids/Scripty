package com.scripty.base.ui.fragments.build;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scripty.base.R;
import com.scripty.base.ui.BaseFragment;
import com.scripty.base.ui.views.CommandLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildFragment extends BaseFragment {

    @BindView(R.id.command_add)
    FloatingActionButton mCommandAdd;

    private static boolean needsUIRerender=false;
    private static List<CommandLayout> views=new ArrayList<>();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        final LinearLayout commandsContainer = root.findViewById(R.id.commands_container);

        // its always 0 .
        Log.e("DDDD",commandsContainer.getChildCount()+"");
        ButterKnife.bind(this,root);
            if (needsUIRerender) {

                    Log.e("dsdsdsd","---------");
                    for (int i = 0 ; i < views.size();i++) {
                        Log.e("sdsdsd","==============");
                        if(commandsContainer.getParent() != null) {
                            ((ViewGroup) commandsContainer.getParent()).removeView(commandsContainer);
                            commandsContainer.removeAllViews();
                            commandsContainer.removeAllViewsInLayout();
                            commandsContainer.addView(views.get(i));
                        }
                    }

                needsUIRerender=false;
            }

        mCommandAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(_BaseActivity);

                @SuppressLint("InflateParams")
                final View customLayout = _BaseActivity.getLayoutInflater().inflate(R.layout.command_add_dialog, null);
                builder.setView(customLayout);

                Button btnOk = customLayout.findViewById(R.id.ok);
                Button btnCancel = customLayout.findViewById(R.id.cancel);

                final AlertDialog dialog = builder.create();
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {}});

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // append the command
                        CommandLayout command = new CommandLayout(_BaseActivity);
                        commandsContainer.removeView(command);
                        commandsContainer.addView(command);


                        needsUIRerender=true;
                        views.add(command);
                        Log.e("DDDD",commandsContainer.getChildCount()+"");

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
        });


       // if(needsUISave) s.saveState(this,"build");
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
