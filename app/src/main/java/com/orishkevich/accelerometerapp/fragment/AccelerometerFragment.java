package com.orishkevich.accelerometerapp.fragment;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.orishkevich.accelerometerapp.R;
import com.orishkevich.accelerometerapp.adapter.AccelAdapter;
import com.orishkevich.accelerometerapp.model.AccelModel;
import com.orishkevich.accelerometerapp.model.ArrayAccelModel;
import com.orishkevich.accelerometerapp.service.ServiceAccel;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.Context.BIND_AUTO_CREATE;


public class AccelerometerFragment extends Fragment {

    private Button btnStart;
    private Button btnStop;
    private static SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss:SS a");
    private ArrayList<AccelModel> valuesAccelArrays = new ArrayList<AccelModel>();
    private ArrayAccelModel arrayAccelModel = null;
    private RecyclerView rvMain;
    private AccelAdapter accelAdapter;
    private LinearLayoutManager layoutManager;


    private SharedPreferences sharedPrefs;
    private String myPrefs = "myPrefs";
    private static final String valuesAccelArraysList = "valuesAccelArraysList";


    private final String LOG_TAG = "AccelerometerFragment";
    public final static String BROADCAST_ACTION = "com.orishkevich.accelerometerapp";
    public final static String PARAM_JSON = "JSON";
    private boolean bound = false;
    private ServiceConnection sConn;
    private Intent intent;
    private ServiceAccel myService;
    private BroadcastReceiver br;

    public AccelerometerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
     /*   if(sharedPrefs.contains(valuesAccelArraysList)) valuesAccelArrays=downSharedPref();
        else valuesAccelArrays=new ArrayList<>();*/
        intent = new Intent(getActivity(), ServiceAccel.class);
        // создаем BroadcastReceiver
        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                Log.d(LOG_TAG, "onReceive:  " + intent.getStringExtra(PARAM_JSON));
                adapterSet(downSharedPref());
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        getActivity().registerReceiver(br, intFilt);


        /**  sConn = new ServiceConnection() {
         public void onServiceConnected(ComponentName name, IBinder binder) {
         Log.d(LOG_TAG, "onServiceConnected");
         bound = true;

         }

         public void onServiceDisconnected(ComponentName name) {

         Log.d(LOG_TAG, "onServiceDisconnected");
         bound = false;

         }
         };*/


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        super.onCreate(savedInstanceState);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnStart = (Button) getActivity().findViewById(R.id.button_start);
        btnStop = (Button) getActivity().findViewById(R.id.button_stop);

        // adapterSet();//создание списка
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (bound == false) {
                    bound = true;
                    getActivity().startService(new Intent(getActivity(), ServiceAccel.class));
                    Log.d("AccelerometerFragment", "Start: " + sdf.format(System.currentTimeMillis()));
                } else
                    Toast.makeText(getActivity(), "Служба уже запущенна", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                bound = false;
                Log.d("AccelerometerFragment", "End: " + sdf.format(System.currentTimeMillis()));
                getActivity().stopService(new Intent(getActivity(), ServiceAccel.class));
                //long start=valuesAccelArrays.get(0).getMil();
                //long end=valuesAccelArrays.get(valuesAccelArrays.size()-1).getMil();

            }

        });
    }


    public void adapterSet(ArrayList<AccelModel> valuesAccelArrays) {
        //downSharedPref();
        rvMain = (RecyclerView) getActivity().findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        accelAdapter = new AccelAdapter(valuesAccelArrays);
        rvMain.setAdapter(accelAdapter);

        accelAdapter.setOnItemClickListener(new AccelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(br);
        //clearSharedPref();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //clearSharedPref();
    }

    public void clearSharedPref() {
        Log.d(LOG_TAG, "clearSharedPref()");
        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(valuesAccelArraysList);
        editor.clear();
        editor.apply();
    }

    public ArrayList<AccelModel> downSharedPref() {

        sharedPrefs = getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        if (sharedPrefs.contains(valuesAccelArraysList)) {
            Log.d(LOG_TAG, "downSharedPref()");
            String sP = sharedPrefs.getString(valuesAccelArraysList, "");
            arrayAccelModel = new Gson().fromJson(sP, ArrayAccelModel.class);
            return valuesAccelArrays = arrayAccelModel.getAccelModel();
        } else {
            Log.d(LOG_TAG, "DOESNT downSharedPref()");
            return valuesAccelArrays = new ArrayList<>();
        }


    }


}
