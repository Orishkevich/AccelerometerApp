package com.orishkevich.accelerometerapp;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class AccelerometerFragment extends Fragment {
    Button btnStart;
    Button btnStop;
    public AccelerometerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        super.onCreate(savedInstanceState);


        return rootView;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = (Button) getActivity().findViewById(R.id.button_start);
        btnStop = (Button) getActivity().findViewById(R.id.button_stop);

        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                getActivity().startService(
                        new Intent(getActivity(), ServiceAccel.class));
            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                getActivity().stopService(
                        new Intent(getActivity(), ServiceAccel.class));
            }
        });
    }


}
