package com.exchange.almulla.ui.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.exchange.almulla.R;

import java.util.Locale;

public class StopWatchFragment extends Fragment {

    private int seconds = 0;
    private TextView txt_timer;
    private boolean isRunning=false;
    private ImageButton btn_paus;
    private ImageButton btn_stop;
    private ImageButton btn_play;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        initControlls(view);
        return view;


    }

    private void initControlls(View view) {
        txt_timer = view.findViewById(R.id.txt_timer);
        btn_paus= view.findViewById(R.id.btn_paus);
        btn_stop= view.findViewById(R.id.btn_stop);
        btn_play= view.findViewById(R.id.btn_play);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCountdown();
            }
        });
        btn_paus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                seconds = 0;
            }
        });

        initTimmer();
    }

    private void initTimmer() {

        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(),"%d:%02d:%02d", hours,minutes, secs);

                txt_timer.setText(time);

                if (isRunning) {
                    seconds++;
                }

                handler.postDelayed(this, 1000);
            }
        });

    }

    private void startCountdown()
    {
        isRunning = true;
    }

}
