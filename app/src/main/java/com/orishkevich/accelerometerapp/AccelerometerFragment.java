package com.orishkevich.accelerometerapp;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.SENSOR_SERVICE;


public class AccelerometerFragment extends Fragment {

    private TextView tvText;
    private SensorManager sensorManager;
    private Sensor sensorAccel;
    private Sensor sensorLinAccel;
    private Sensor sensorGravity;

    private StringBuilder sb = new StringBuilder();
    private  Timer timer;


    private float[] valuesAccel = new float[3];
    private  float[] valuesAccelMotion = new float[3];
    private float[] valuesAccelGravity = new float[3];
    private float[] valuesLinAccel = new float[3];
    private float[] valuesGravity = new float[3];

    public AccelerometerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Fragment", "onViewCreate");


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        tvText = (TextView)getActivity().findViewById(R.id.tvText);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorLinAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        return rootView;
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorLinAccel,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listener, sensorGravity,SensorManager.SENSOR_DELAY_NORMAL);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showInfo();
                    }
                });
            }
        };
        timer.schedule(task, 0, 400);

    }

    @Override
   public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener);
        timer.cancel();
    }

    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1],
                values[2]);
    }

    void showInfo() {
        sb.setLength(0);
        sb.append("Accelerometer: " + format(valuesAccel))
                .append("\n\nAccel motion: " + format(valuesAccelMotion))
                .append("\nAccel gravity : " + format(valuesAccelGravity))
                .append("\n\nLin accel : " + format(valuesLinAccel))
                .append("\nGravity : " + format(valuesGravity));
        Log.d("Fragment", ""+sb);
    }



    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3; i++) {
                        valuesAccel[i] = event.values[i];
                        valuesAccelGravity[i] = (float) (0.1 * event.values[i] + 0.9 * valuesAccelGravity[i]);
                        valuesAccelMotion[i] = event.values[i]
                                - valuesAccelGravity[i];
                    }
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    for (int i = 0; i < 3; i++) {
                        valuesLinAccel[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_GRAVITY:
                    for (int i = 0; i < 3; i++) {
                        valuesGravity[i] = event.values[i];
                    }
                    break;
            }

        }

    };
}
