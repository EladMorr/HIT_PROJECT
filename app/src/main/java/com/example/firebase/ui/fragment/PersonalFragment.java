package com.example.firebase.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.firebase.R;

import java.util.Timer;
import java.util.TimerTask;

public class PersonalFragment extends Fragment {

    ImageButton circleClockBTN;
    boolean timerStarted = false;
    TextView mClock;
    private long mStartTime;
    private Timer mTimer;

    int hours, minutes, seconds;

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
                if (timerStarted) {
                    stopClock();
                } else {
                    startClock();
                }
            }
        });

        return v;
    }

    private void startClock() {
        timerStarted = true;
        mStartTime = System.currentTimeMillis();
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateClock();
            }
        }, 0, 1000);
    }

    private void stopClock() {
        timerStarted = false;
        mTimer.cancel();
    }

    private void updateClock() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - mStartTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                mClock.setText(String.format("%d:%02d", minutes, seconds));
            }
        });
    }
}
