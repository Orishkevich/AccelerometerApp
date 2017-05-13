package com.orishkevich.accelerometerapp.service;
/**
 * Из своего приложения службу можно запустить вызовом метода Context.startService(), остановить через Context.stopService().
 * Служба может остановить сама себя, вызывая методы Service.stopSelf() или Service.stopSelfResult().
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orishkevich.accelerometerapp.model.Session;
import com.orishkevich.accelerometerapp.model.ArrayAccelModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.orishkevich.accelerometerapp.fragment.AccelerometerFragment.BROADCAST_ACTION;

public class ServiceAccel extends Service {


    private static final String PARAM_JSON = "JSON";
    private SensorManager sensorManager;
    private Sensor sensorAccel;
    private StringBuilder sb = new StringBuilder();
    private Timer timer;
    private float[] valuesAccel = new float[3];
    private ArrayAccelModel arrayAccelModel= new ArrayAccelModel();;
    private ArrayList<Session> valuesAccelArrays;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss: a");
    private SharedPreferences sharedPrefs;
    public String myPrefs = "myPrefs";
    public static final String valuesAccelArraysList = "valuesAccelArraysList";
    final String LOG_TAG = "Service";
    private int time;
    public static final String userName= "userName";

    public ServiceAccel() {
    }


    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind");
        return new Binder();
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "onRebind");
    }

    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "onUnbind");
        return super.onUnbind(intent);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        sharedPrefs = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //vb     clearSharedPref();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (sharedPrefs.contains(userName)) {
            arrayAccelModel.setUser(sharedPrefs.getString(userName, ""));

        } else {
            arrayAccelModel.setUser("Anonymous");
                 }
        time=1000;//задать время
        Log.d("Service", "onStartCommand");
        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        valuesAccelArrays=new ArrayList<Session>();
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        valuesAccelArrays.add(new Session(valuesAccel[0], valuesAccel[1], valuesAccel[2], System.currentTimeMillis()));
                        showInfo();
                        Log.d(LOG_TAG, "TIME:" + System.currentTimeMillis());
                        // TODO Здесь можно прописать одну строчку кода для отправки данных сразу на Firebase
                    }
                });
            }
        };
        timer.schedule(task, 0, time);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service", " onDestroy()");
        Toast.makeText(this, "Служба остановлена", Toast.LENGTH_SHORT).show();
        timer.cancel();


        arrayAccelModel.setArray(valuesAccelArrays);
        arrayAccelModel.setSession(sdf.format(valuesAccelArrays.get(0).getMil()));
        saveSharedPref(arrayAccelModel);

        //
        Intent intent = new Intent(BROADCAST_ACTION);
        Log.d(LOG_TAG, "BROADCAST_ACTION");
        // сообщаем о старте задачи
        intent.putExtra(PARAM_JSON, "Служба остановлена");
        sendBroadcast(intent);


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

                    }
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    for (int i = 0; i < 3; i++) {

                    }
                    break;
                case Sensor.TYPE_GRAVITY:
                    for (int i = 0; i < 3; i++) {

                    }
                    break;
            }

        }

    };

    void showInfo() {
        sb.setLength(0);
        sb.append("Accelerometer: " + format(valuesAccel));
        //  Log.d("Sensor", ""+sb);
    }

    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2]);
    }

    public void clearSharedPref() {
        Log.d("Service", "clearSharedPref()");
        sharedPrefs = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(valuesAccelArraysList);
        editor.clear();
        editor.apply();
    }

    public void saveSharedPref(ArrayAccelModel arrayAccelModel) {
        Log.d("Service", "saveSharedPref()");
        String arrayAccelModels = new Gson().toJson(arrayAccelModel, ArrayAccelModel.class);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(valuesAccelArraysList, arrayAccelModels);
        editor.apply();
    }


}




