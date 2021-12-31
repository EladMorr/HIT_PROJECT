package com.example.firebase.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.firebase.R;

import java.util.Timer;

public class PersonalFragment extends Fragment {

    ImageButton circleClockBTN;
    boolean timerStarted = false;
    TextView mClock;
    public PersonalFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personal_page, container, false);

        circleClockBTN = v.findViewById(R.id.circleBTN);
        mClock = v.findViewById(R.id.timerTXT);
        circleClockBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClock.setText("00:00:00");
            }
        });

        return v;
    }

}
