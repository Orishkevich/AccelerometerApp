package com.orishkevich.accelerometerapp.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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

    private  Button btnStart;
    private  Button btnStop;
    private static SimpleDateFormat sdf=new SimpleDateFormat("MMM MM dd, yyyy hh:mm:ss:SS a");
    private ArrayList<AccelModel> valuesAccelArrays=new ArrayList <AccelModel>();
    private ArrayAccelModel arrayAccelModel=null;
    public RecyclerView rvMain;
    public AccelAdapter accelAdapter;
    public LinearLayoutManager layoutManager;


    private SharedPreferences sharedPrefs;
    public String myPrefs = "myPrefs";
    public static final String valuesAccelArraysList = "valuesAccelArraysList";


    final String LOG_TAG = "myLogs";

    boolean bound = false;
    public ServiceConnection sConn;
    public Intent intent;
    public ServiceAccel myService;


    public AccelerometerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

        if(sharedPrefs.contains(valuesAccelArraysList)) valuesAccelArrays=downSharedPref();
        else valuesAccelArrays=new ArrayList<>();


        intent = new Intent(getActivity(), ServiceAccel.class);
        sConn = new ServiceConnection() {

            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.d(LOG_TAG, "AccelerometerFragment onServiceConnected");
                bound = true;

                }

            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "AccelerometerFragment onServiceDisconnected");
                bound = false;
            }
        };

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

       // adapterSet();//создание списка
        btnStart.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
              /*  getActivity().startService(new Intent(getActivity(), ServiceAccel.class));
                Log.d("AccelerometerFragment", "Start: "+sdf.format(System.currentTimeMillis()));
                //accelAdapter.setCleaner();//очистка списка*/
                getActivity().bindService(intent, sConn, BIND_AUTO_CREATE);

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
              /*  Log.d("AccelerometerFragment", "End: "+sdf.format(System.currentTimeMillis()));
                getActivity().stopService(new Intent(getActivity(), ServiceAccel.class));
                adapterSet();
                //accelAdapter.notifyDataSetChanged();
               // rvMain.setAdapter(accelAdapter);
                //accelAdapter.setUpdate(valuesAccelArrays);

                //long start=valuesAccelArrays.get(0).getMil();
                //long end=valuesAccelArrays.get(valuesAccelArrays.size()-1).getMil();
                //Log.d("AccelerometerFragment", "Time="+((end-start)/1000));
                //accelAdapter.notifyDataSetChanged();
                //rvMain.setAdapter(new AccelAdapter(valuesAccelArrays));
                //rvMain.invalidate();*/
                if (!bound) return;
                getActivity().unbindService(sConn);
                bound = false;
            }

        });
    }


    public void adapterSet(){
        Log.d("AccelerometerFragment", "New Adapter");
        downSharedPref();
        rvMain = (RecyclerView)getActivity().findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMain.setLayoutManager(layoutManager);
        accelAdapter = new AccelAdapter(valuesAccelArrays);
        rvMain.setAdapter(accelAdapter);

        accelAdapter.setOnItemClickListener(new AccelAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View v, int position){

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //clearSharedPref();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //clearSharedPref();
    }
    public void  clearSharedPref(){
        Log.d("AccelerometerFragment", "clearSharedPref()");
        sharedPrefs = this.getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(valuesAccelArraysList);
        editor.clear();
        editor.apply();
    }
    public ArrayList<AccelModel>  downSharedPref(){

        sharedPrefs =getActivity().getSharedPreferences(myPrefs, Context.MODE_PRIVATE);
        if(sharedPrefs.contains(valuesAccelArraysList)) {
            Log.d("AccelerometerFragment", "downSharedPref()");
            String sP = sharedPrefs.getString(valuesAccelArraysList, "");
            arrayAccelModel = new Gson().fromJson(sP, ArrayAccelModel.class);
            return valuesAccelArrays = arrayAccelModel.getAccelModel();
        }
        else {
            Log.d("AccelerometerFragment", "DOESNT downSharedPref()");
            return valuesAccelArrays=new ArrayList<>();}



    }


}
