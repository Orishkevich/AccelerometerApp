package com.orishkevich.accelerometerapp.service;
/**

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
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.DaysModel;
import com.orishkevich.accelerometerapp.model.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.orishkevich.accelerometerapp.fragment.AccelerometerFragment.BROADCAST_ACTION;

public class ServiceAccel extends Service {

    private int i = 0;
    private static final String PARAM_JSON = "JSON";
    private SensorManager sensorManager;
    private Sensor sensorAccel;
    private StringBuilder sb = new StringBuilder();
    private Timer timer;
    private float[] valuesAccel = new float[3];
    private Session session = new Session();
     private ArrayList<AccelModel> valuesAccelArrays;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss: a");
    private static SimpleDateFormat sdfDay = new SimpleDateFormat("MMM MM dd, yyyy");
    private SharedPreferences sharedPrefs;
    public String myPrefs = "myPrefs";
    public static final String valuesAccelArraysList = "valuesAccelArraysList";
    private static final String daySessionSP = "daySessionSP";
    final String LOG_TAG = "Service";
    private int time;
    private int period;
    private Date date;
    public static final String userName = "userName";
    public static DaysModel dm = new DaysModel();

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

        //clearSharedPref();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        time = intent.getIntExtra("time", 1000);
        //  period=intent.getIntExtra("period",1000);
        if (0 == (intent.getIntExtra("period", 1000))) {
            period = Integer.MAX_VALUE;
            Log.d(LOG_TAG, "MAX_VALUE");
        } else period = intent.getIntExtra("period", 1000);

        if (sharedPrefs.contains(userName)) {
            session.setUser(sharedPrefs.getString(userName, ""));

        } else {
            session.setUser("Anonymous");
        }
        // time=1000;//задать время

        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        valuesAccelArrays = new ArrayList<AccelModel>();
        sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL);
        timer = new Timer();
        //  date=new Date(System.currentTimeMillis()+period);
        // timerSet(date);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        if (i < (period / time))

                        {
                            Log.d(LOG_TAG, "Iterator:" + i);
                            valuesAccelArrays.add(new AccelModel(valuesAccel[0], valuesAccel[1], valuesAccel[2], System.currentTimeMillis()));
                            showInfo();
                            i++;
                            // TODO Здесь можно прописать одну строчку кода для отправки данных сразу на Firebase
                        } else {

                            destroyService();
                        }
                    }
                });
            }
        };
        // timer.schedule(task, 0, time);
        timer.scheduleAtFixedRate(task, 0, time);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, " onDestroy()");
        if (!(i == 0)) destroyService();


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

    public void saveSharedPref(Session session) {
        Log.d("Service", "saveSharedPref()");
        String arrayAccelModels = new Gson().toJson(session, Session.class);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(valuesAccelArraysList, arrayAccelModels);
        editor.apply();

        //dm.setDateName(sdfDay.format(System.currentTimeMillis()));
        // dm.getSessions().add(session);
        // saveDaySessionsSP(dm) ;
    }

    public void saveDaySessionsSP(DaysModel dm) {
        Log.d("Service", "saveSharedPref()");
        String dayModels = new Gson().toJson(dm, DaysModel.class);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(daySessionSP, dayModels);
        editor.apply();
    }

    public void destroyService() {
        i = 0;
        Toast.makeText(this, "Служба остановлена", Toast.LENGTH_SHORT).show();
        timer.cancel();
        session.setArray(valuesAccelArrays);
        session.setSession(sdf.format(valuesAccelArrays.get(0).getMil()));


        saveSharedPref(session);

        Intent intent = new Intent(BROADCAST_ACTION);

        intent.putExtra(PARAM_JSON, "Служба остановлена");
        sendBroadcast(intent);
    }

}




