package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

/**
 * Created by Swapnil on 25/01/22.
 */

public class CommercialNextDialog extends Fragment {
    private static final String TAG = "CommercialNextDialog";
    Button btnSubmit;
    RadioButton rb_greaterThan45, rb_46To75, rb_76To95, rb_greaterThan95;
    String segregationLevel;

    public CommercialNextDialog() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commercial_next_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnSubmit = view.findViewById(R.id.btnSubmit);
        rb_greaterThan45 = view.findViewById(R.id.rb_greaterThan45);
        rb_46To75 = view.findViewById(R.id.rb_46To75);
        rb_76To95 = view.findViewById(R.id.rb_76To95);
        rb_greaterThan95 = view.findViewById(R.id.rb_greaterThan95);
        registerEvents();
        btnSubmit.setOnClickListener(v -> {
            onSubmit(segregationLevel);
        });
    }


    private void registerEvents() {


        rb_greaterThan45.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                segregationLevel = ">45%";
            }
        });

        rb_46To75.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                segregationLevel = "46-75%";
            }
        });

        rb_76To95.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                segregationLevel = "76-95%";
            }
        });

        rb_greaterThan95.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                segregationLevel = ">95%";
            }
        });

    }


    void onSubmit(String segregationLevel) {
        CommercialNextDialog.SecondDialog listener = (CommercialNextDialog.SecondDialog) getParentFragment();
        listener.onSubmit(segregationLevel);
    }


    public interface SecondDialog {
        void onSubmit(String segregationLevel);
    }
}