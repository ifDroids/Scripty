package com.scripty.base.ui.build;

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
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scripty.base.R;
import com.scripty.base.ui.BaseFragment;
import com.scripty.base.ui.views.CommandLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildFragment extends BaseFragment {

    @BindView(R.id.command_add)
    FloatingActionButton mCommandAdd;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final LinearLayout commandsContainer = root.findViewById(R.id.commands_container);

        ButterKnife.bind(this,root);

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
                        commandsContainer.addView(command);
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

        return root;
    }

}
