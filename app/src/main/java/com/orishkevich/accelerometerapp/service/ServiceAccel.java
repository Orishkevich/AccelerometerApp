package com.orishkevich.accelerometerapp.service;
/**
 * Из своего приложения службу можно запустить вызовом метода Context.startService(), остановить через Context.stopService().
 * Служба может остановить сама себя, вызывая методы Service.stopSelf() или Service.stopSelfResult().*/
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
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.ArrayAccelModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ServiceAccel extends Service {
    public MyBinder binder = new MyBinder();


    private Gson gson = new Gson();
    private TextView tvText;
    private SensorManager sensorManager;
    private Sensor sensorAccel;
    private Sensor sensorLinAccel;
    private Sensor sensorGravity;
    private StringBuilder sb = new StringBuilder();
    private Timer timer;

    private float[] valuesAccel = new float[3];
    private ArrayAccelModel arrayAccelModel;

    private ArrayList <AccelModel> valuesAccelArrays=new ArrayList <AccelModel>();

    //private  float[] valuesAccelMotion = new float[3];
   // private float[] valuesAccelGravity = new float[3];
   // private float[] valuesLinAccel = new float[3];
  //  private float[] valuesGravity = new float[3];

    private static SimpleDateFormat sdf=new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss:SS a");

    private SharedPreferences sharedPrefs;
    public String myPrefs = "myPrefs";
    public static final String valuesAccelArraysList = "valuesAccelArraysList";
    final String LOG_TAG = "myLogs";

    public ServiceAccel() {
    }


    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "MyService onBind");
        return new Binder();
    }

    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(LOG_TAG, "MyService onRebind");
    }

    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "MyService onUnbind");
        return super.onUnbind(intent);
    }



    @Override
    public void onCreate() {
        super.onCreate();

        sharedPrefs = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorLinAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
                //sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        //clearSharedPref();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clearSharedPref();
        Log.d("Service", "onStartCommand");
        Toast.makeText(this, "Служба запущена",Toast.LENGTH_SHORT).show();
        //Log.d("Service", "SimpleDateFormat="+sdf.format(System.currentTimeMillis()));//"MMM MM dd, yyyy h:mm a".
        //Log.d("Service", "Start: "+sdf.format(System.currentTimeMillis()));
        sensorManager.registerListener(listener, sensorAccel,SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(listener, sensorLinAccel,SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(listener, sensorGravity,SensorManager.SENSOR_DELAY_NORMAL);
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        valuesAccelArrays.add(new AccelModel(valuesAccel[0],valuesAccel[1],valuesAccel[2], System.currentTimeMillis()));
                       // String json = gson.toJson(new AccelModel(valuesAccel[0],valuesAccel[1],valuesAccel[2], System.currentTimeMillis()));
                       // Log.d("Service", "Json="+json);
                        showInfo();
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Service", " onDestroy()");
        Toast.makeText(this, "Служба остановлена",Toast.LENGTH_SHORT).show();
       // timer.cancel();
        //Log.d("Service", "End: "+sdf.format(System.currentTimeMillis()));
        arrayAccelModel=new ArrayAccelModel(valuesAccelArrays);
        saveSharedPref(arrayAccelModel);

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
                        //valuesAccelGravity[i] = (float) (0.1 * event.values[i] + 0.9 * valuesAccelGravity[i]);
                       //valuesAccelMotion[i] = event.values[i]- valuesAccelGravity[i];
                    }
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    for (int i = 0; i < 3; i++) {
                        //valuesLinAccel[i] = event.values[i];
                    }
                    break;
                case Sensor.TYPE_GRAVITY:
                    for (int i = 0; i < 3; i++) {
                        //valuesGravity[i] = event.values[i];
                    }
                    break;
            }

        }

    };

    void showInfo() {
        sb.setLength(0);
        sb.append("Accelerometer: " + format(valuesAccel))
               // .append("\n\nAccel motion: " + format(valuesAccelMotion))
               // .append("\nAccel gravity : " + format(valuesAccelGravity))
               // .append("\n\nLin accel : " + format(valuesLinAccel))
                //.append("\nGravity : " + format(valuesGravity))
        ;
      //  Log.d("Sensor", ""+sb);
    }

    String format(float values[]) {
        return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1],values[2]);
    }

    public void  clearSharedPref(){
        Log.d("Service", "clearSharedPref()");
        sharedPrefs = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(valuesAccelArraysList);
        editor.clear();
        editor.apply();
    }

    public void  saveSharedPref( ArrayAccelModel arrayAccelModel){
        Log.d("Service", "saveSharedPref()");
        String arrayAccelModels=new Gson().toJson(arrayAccelModel, ArrayAccelModel.class);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(valuesAccelArraysList, arrayAccelModels);
        editor.apply();
    }

    public class MyBinder extends Binder {
        public ServiceAccel getService() {
            return ServiceAccel.this;
        }
}



}
